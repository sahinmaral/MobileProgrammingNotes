package com.example.cookingrecipeapp

class FoodRecipe {
    private var name : String
    private var ingredients : String
    private var image : ByteArray?

    fun getName(): String{
        return this.name
    }

    fun getIngredients(): String {
        return this.ingredients
    }

    fun getImage(): ByteArray? {
        return this.image
    }

    fun setImage(image:ByteArray?){
        this.image = image
    }

    fun setName(name:String){
        this.name = name
    }

    fun setIngredients(ingredients:String){
        this.ingredients = ingredients
    }

    constructor(name:String,ingredients:String,image:ByteArray?){
        this.name = name
        this.ingredients = ingredients
        this.image = image
    }


}