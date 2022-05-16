/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modifications 
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to 
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {
	
	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	
	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
	}
	
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "asdf", "3");
	}
	
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than 
	 * 		the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffee() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
		
	}
	/**
	 * Given a coffee maker with no valid recipe for position 4
	 * When we make coffee, selecting the not valid recipe and paying more than 
	 * 		the coffee costs, throws exception
	 */
	
	@Test
	public void testMakeCoffeeOutofBounds() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(75, coffeeMaker.makeCoffee(8, 75));
		
	}
	
	@Test
	public void testMakeCoffeeMinus() throws IndexOutOfBoundsException {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(75, coffeeMaker.makeCoffee(-1, 75));
		
	}
	@Test
	public void testMakeCoffeeNull() throws NullPointerException {
		coffeeMaker.addRecipe(recipe1);
		Recipe [] recipearray= coffeeMaker.getRecipes();
		recipearray[0] = null;
		assertEquals(75, coffeeMaker.makeCoffee(0, 75));
		
	}
	
	@Test
	public void testMakeCoffeeInventory() {
		Inventory inv = new Inventory();
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.makeCoffee(0, 75);
		assertEquals(12, inv.getCoffee());
		assertEquals(15, inv.getChocolate());
		assertEquals(14, inv.getMilk());
		assertEquals(14, inv.getSugar());
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
		
	}
	
	
	
	/**
	 * Adding a recipe, the recipe should be returned
	 */
	@Test
	public void testAddRecipe() {
		coffeeMaker.addRecipe(recipe1);
		Recipe [] recipearray= coffeeMaker.getRecipes();
		assertEquals(recipe1, recipearray[0]);
		
		coffeeMaker.addRecipe(recipe2);
		recipearray= coffeeMaker.getRecipes();
		assertEquals(recipe2, recipearray[1]);
		
	}

	@Test
	public void testDeleteRecipe() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		assertEquals("Mocha", coffeeMaker.deleteRecipe(1));
		assertEquals(null, coffeeMaker.deleteRecipe(3));
	}
	
	@Test
	public void testDeleteRecipeNegative() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(null, coffeeMaker.deleteRecipe(-1));
	}
	
	@Test
	public void testDeleteRecipeOver() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		assertEquals(null, coffeeMaker.deleteRecipe(3));
	}
	
	@Test
	public void testSetName() {
		recipe1.setName("Koffie");
		coffeeMaker.addRecipe(recipe1);
		assertEquals("Koffie", coffeeMaker.deleteRecipe(0));
	}
	
	@Test
	public void testSetNameNull() {
		recipe1.setName(null);
		coffeeMaker.addRecipe(recipe1);
		assertEquals("Coffee", coffeeMaker.deleteRecipe(0));
	}
	
	@Test
	public void testEditRecipeNegative() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		assertEquals(null, coffeeMaker.editRecipe(-1, recipe3));
		assertEquals("Coffee", coffeeMaker.editRecipe(0, recipe4));
		assertEquals("Coffee", recipe4.getName());
	}
	
	@Test
	public void testEditRecip() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		assertEquals("Coffee", coffeeMaker.editRecipe(0, recipe3));
		Recipe [] recipearray= coffeeMaker.getRecipes();
		assertEquals(recipe3, recipearray[0]);
		assertEquals("Coffee", recipe3.getName());
		assertEquals(0, recipearray[0].getAmtChocolate());
		assertEquals(3, recipearray[0].getAmtMilk());
		assertEquals(3, recipearray[0].getAmtCoffee());
		assertEquals(1, recipearray[0].getAmtSugar());
		assertEquals(100, recipearray[0].getPrice());
	}
	
	@Test
	public void testEditRecipe() {
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.addRecipe(recipe4);
		assertEquals("Hot Chocolate", coffeeMaker.editRecipe(1, recipe1));
		Recipe [] recipearray= coffeeMaker.getRecipes();
		assertEquals(recipe1, recipearray[1]);
		assertEquals(null, coffeeMaker.editRecipe(3,recipe4));
	}
	
	@Test
	public void testChocolate() 
	{
		Inventory inv = new Inventory();
		assertEquals(15, inv.getChocolate());
	}
	
	@Test (expected = AssertionError.class)
	public void testsetChocolate() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.setChocolate(-3);
		assertEquals(-3, inv.getChocolate());
	}
	
	@Test
	public void testaddChocolate() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.addChocolate("25");
		assertEquals(40, inv.getChocolate());
	}
	
	@Test (expected = InventoryException.class)
	public void testaddChocolateExcpetion() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.addChocolate("-3");
		//assertEquals(12, inv.getChocolate());
	}
	
	@Test
	public void testCoffee() 
	{
		Inventory inv = new Inventory();
		assertEquals(15, inv.getCoffee());
	}
	
	@Test (expected = AssertionError.class)
	public void testsetCoffee() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.setCoffee(-3);
		assertEquals(-3, inv.getCoffee());
	}
	
	@Test
	public void testaddCoffee() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.addCoffee("25");
		assertEquals(40, inv.getCoffee());
	}
	
	@Test (expected = InventoryException.class)
	public void testaddCoffeeExcpetion() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.addCoffee("-3");
		//assertEquals(12, inv.getChocolate());
	}
	
	@Test
	public void testMilk() 
	{
		Inventory inv = new Inventory();
		assertEquals(15, inv.getMilk());
	}
	
	@Test (expected = AssertionError.class)
	public void testsetMilk() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.setMilk(-3);
		assertEquals(-3, inv.getMilk());
	}
	
	@Test
	public void testaddMilk() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.addMilk("25");
		assertEquals(40, inv.getMilk());
	}
	
	@Test (expected = InventoryException.class)
	public void testaddMilkExcpetion() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.addMilk("-3");
		//assertEquals(12, inv.getChocolate());
	}
	
	@Test
	public void testSugar() 
	{
		Inventory inv = new Inventory();
		assertEquals(15, inv.getSugar());
	}
	
	@Test (expected = AssertionError.class)
	public void testsetSugar() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.setSugar(-3);
		assertEquals(-3, inv.getSugar());
	}
	
	@Test
	public void testaddSugar() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.addSugar("25");
		assertEquals(40, inv.getSugar());
	}
	
	@Test (expected = InventoryException.class)
	public void testaddSugarExcpetion() throws InventoryException 
	{
		Inventory inv = new Inventory();
		inv.addSugar("-3");
		//assertEquals(12, inv.getChocolate());
	}
	
	@Test
	public void testEnoughIngredients() 
	{
		Inventory inv = new Inventory();
		inv.setChocolate(1);
		//coffeeMaker.addRecipe(recipe2);
		//inv.useIngredients(recipe2);
		assertEquals(false, inv.enoughIngredients(recipe2));
	}
	
	@Test
	public void testUseIngredients() 
	{
		Inventory inv = new Inventory();
		//coffeeMaker.addRecipe(recipe3);
		inv.useIngredients(recipe3);
		assertEquals(12, inv.getMilk());
		
	}
	
	@Test
	public void testUseIngredientsFalse() 
	{
		Inventory inv = new Inventory();
		inv.setSugar(0);
		//coffeeMaker.addRecipe(recipe3);
		inv.useIngredients(recipe3);
		assertEquals(false, inv.useIngredients(recipe3));
		
	}
	
	
	
	
	
	
	

	
	
	

}
