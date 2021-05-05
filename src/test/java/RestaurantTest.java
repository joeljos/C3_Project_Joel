import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    int initialMenuSize;

    @BeforeEach
    private void setup() {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        initialMenuSize = restaurant.getMenu().size();
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime betweenTime = openingTime.plusMinutes(5);
        Restaurant spy = Mockito.spy(restaurant);
        Mockito.when(spy.getCurrentTime()).thenReturn(openingTime);
        assertTrue(spy.isRestaurantOpen());
        Mockito.when(spy.getCurrentTime()).thenReturn(betweenTime);
        assertTrue(spy.isRestaurantOpen());
        Mockito.when(spy.getCurrentTime()).thenReturn(closingTime);
        assertTrue(spy.isRestaurantOpen());
    }
    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime beforeOpeningTime = openingTime.minusMinutes(5);
        LocalTime afterClosingTime = closingTime.plusMinutes(5);
        Restaurant spy = Mockito.spy(restaurant);
        Mockito.when(spy.getCurrentTime()).thenReturn(beforeOpeningTime);
        assertFalse(spy.isRestaurantOpen());
        Mockito.when(spy.getCurrentTime()).thenReturn(afterClosingTime);
        assertFalse(spy.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<<<<<<<<<<orderTotal>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void orderTotal_should_return_negative1_if_selectedItemNameList_is_empty(){
        List <String> selectedItemNames = new ArrayList<String>();
        assertEquals(-1,restaurant.orderTotal(selectedItemNames));
    }
    @Test
    public void orderTotal_should_be_correctly_returned(){
        List <String> selectedItemNames = new ArrayList<String>();
        selectedItemNames.add("Sweet corn soup");
        selectedItemNames.add("Vegetable lasagne");
        assertEquals(388,restaurant.orderTotal(selectedItemNames));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<orderTotal>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}