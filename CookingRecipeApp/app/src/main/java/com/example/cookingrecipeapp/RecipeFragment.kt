package com.example.cookingrecipeapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import java.io.ByteArrayOutputStream

class RecipeFragment : Fragment() {

    lateinit var editTextFoodName: EditText
    lateinit var editTextFoodIngredients : EditText
    lateinit var buttonSaveOrUpdateRecipe : Button
    lateinit var imageViewRecipe: ImageView

    private var choosedImage : Uri? = null
    private var choosedImageBitmap : Bitmap? = null

    private lateinit var locationBeforeRecipeFragment: LocationBeforeRecipeFragment
    private var foodId : Int = 0

    private val galleryAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        choosedImage = it.data?.data
        try {
            context?.let {
                if(choosedImage != null){
                    if(Build.VERSION.SDK_INT >= 28){
                        val source = ImageDecoder.createSource(it.contentResolver,choosedImage!!)
                        choosedImageBitmap = ImageDecoder.decodeBitmap(source)
                        // If size of the image is too large , we cannot see what's at the image.
                        imageViewRecipe.setImageBitmap(shrinkBitmap(choosedImageBitmap!!,300))
                    }else{
                        choosedImageBitmap = MediaStore.Images.Media.getBitmap(it.contentResolver,choosedImage)
                        imageViewRecipe.setImageBitmap(shrinkBitmap(choosedImageBitmap!!,300))
                    }


                }
            }

        }catch (exception:Exception){
            exception.printStackTrace()
        }
    }

    private val storagePermissionAction = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted ->
        if(!isGranted){
            context?.let{
                choosedImageBitmap = BitmapFactory.decodeResource(it.resources,R.drawable.image_placeholder)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSaveOrUpdateRecipe = view.findViewById(R.id.buttonSaveOrUpdateRecipe)
        imageViewRecipe = view.findViewById(R.id.imageViewRecipe)
        editTextFoodName = view.findViewById(R.id.editTextFoodName)
        editTextFoodIngredients = view.findViewById(R.id.editTextFoodIngredients)

        arguments?.let{
            locationBeforeRecipeFragment = RecipeFragmentArgs.fromBundle(it).locationBeforeRecipeFragment
            foodId = RecipeFragmentArgs.fromBundle(it).foodId
        }

        if(locationBeforeRecipeFragment == LocationBeforeRecipeFragment.FROM_NEW){
            initializeAsNewRecipe()

        }else if(locationBeforeRecipeFragment == LocationBeforeRecipeFragment.FROM_LIST){
            initializeAsUpdatedRecipe(view,foodId)
        }


    }

    private fun initializeAsUpdatedRecipe(view:View,foodId : Int) {
        buttonSaveOrUpdateRecipe.setOnClickListener {
            updateRecipe(view)
        }
        imageViewRecipe.setOnClickListener {

        }
        buttonSaveOrUpdateRecipe.text = "Update"

        try {
            context?.let {
                val database = it.openOrCreateDatabase("cookingRecipeAppDb", Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS recipes (ID INTEGER PRIMARY KEY,name VARCHAR,ingredients VARCHAR,image BLOB)")
                val cursor = database.rawQuery("SELECT * FROM recipes WHERE ID = '${foodId}'",null)
                var foodNameIndex = cursor.getColumnIndex("name")
                var foodIngredientsIndex = cursor.getColumnIndex("ingredients")
                var foodImageIndex = cursor.getColumnIndex("image")

                while (cursor.moveToNext()){
                    editTextFoodName.setText(cursor.getString(foodNameIndex))
                    editTextFoodIngredients.setText(cursor.getString(foodIngredientsIndex))
                    val imageBlob = cursor.getBlob(foodImageIndex)
                    val bitmap = BitmapFactory.decodeByteArray(imageBlob,0,imageBlob.size)
                    imageViewRecipe.setImageBitmap(bitmap)

                }
            }
        }catch (e:Exception){
            throw e
        }
    }

    private fun initializeAsNewRecipe() {
        buttonSaveOrUpdateRecipe.setOnClickListener {
            saveRecipe(it)
        }
        imageViewRecipe.setOnClickListener {
            selectImage(it)
        }

        buttonSaveOrUpdateRecipe.text = "Save"

        editTextFoodName.setText("")
        editTextFoodIngredients.setText("")
        imageViewRecipe.setImageResource(R.drawable.image_placeholder)
    }

    private fun selectImage(view:View){
        activity?.let {

            if(ContextCompat.checkSelfPermission(it.applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                storagePermissionAction.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }else{
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                galleryAction.launch(galleryIntent)
            }
        }
    }

    private fun shrinkBitmap(bitmap:Bitmap,maximumSize:Int):Bitmap{
        var width = bitmap.width
        var height = bitmap.height

        val bitmapRatio : Double = width.toDouble() / height.toDouble()

        if(bitmapRatio > 1){
            // Horizontal
            width = maximumSize
            val shrinkedHeight = width / bitmapRatio
            height = shrinkedHeight.toInt()
        }else{
            //Vertical
            height = maximumSize
            val shrinkedWidth = height * bitmapRatio
            width = shrinkedWidth.toInt()
        }

        return Bitmap.createScaledBitmap(bitmap,width,height,true)
    }

    private fun saveRecipe(view:View){
        val foodName = editTextFoodName.text.toString()
        val foodIngredients = editTextFoodIngredients.text.toString()

        val outputStream = ByteArrayOutputStream()
        choosedImageBitmap!!.compress(Bitmap.CompressFormat.PNG,50,outputStream)
        val byteArray = outputStream.toByteArray()

        val recipe = FoodRecipe(foodName,foodIngredients,byteArray)
        try {
            saveRecipeToDatabase(view,recipe)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun saveRecipeToDatabase(view:View,recipe:FoodRecipe){
        try {
            context?.let {
                val database = it.openOrCreateDatabase("cookingRecipeAppDb", Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS recipes (ID INTEGER PRIMARY KEY,name VARCHAR,ingredients VARCHAR,image BLOB)")

                val sqlString = "INSERT INTO recipes (name,ingredients,image) VALUES (?,?,?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1,recipe.getName())
                statement.bindString(2,recipe.getIngredients())
                statement.bindBlob(3,recipe.getImage())

                statement.execute()

                val action = RecipeFragmentDirections.actionRecipeFragmentToListFragment()
                Navigation.findNavController(view).navigate(action)
            }
        }catch (e:Exception){
            throw e
        }

    }

    private fun updateRecipe(view:View){
        val foodName = editTextFoodName.text.toString()
        val foodIngredients = editTextFoodIngredients.text.toString()

        var foodRecipe = FoodRecipe(foodName,foodIngredients,null)
        try {
            updateRecipeToDatabase(view,foodRecipe)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun updateRecipeToDatabase(view:View,recipe:FoodRecipe){
        try {
            context?.let{
                val database = it.openOrCreateDatabase("cookingRecipeAppDb", Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS recipes (ID INTEGER PRIMARY KEY,name VARCHAR,ingredients VARCHAR,image BLOB)")

                val statement = database.compileStatement("UPDATE recipes SET name = ? , ingredients = ? WHERE ID = '${foodId}'")
                statement.bindString(1,recipe.getName())
                statement.bindString(2,recipe.getIngredients())
                statement.execute()

                val action = RecipeFragmentDirections.actionRecipeFragmentToListFragment()
                Navigation.findNavController(view).navigate(action)
            }
        }catch (e:Exception){
            throw e
        }
    }
}