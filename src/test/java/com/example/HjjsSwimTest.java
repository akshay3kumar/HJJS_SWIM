package com.example;

import Coach.Coach;
import Lessons.Lesson;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import main.HjjsSwim;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HjjsSwimTest {
    
     @Test
    public void testAddLesson() {
        System.out.println("=========Test to Add Lesson Started===========");
        // Create an instance of HJSSSoftware
        HjjsSwim hjssSoftware = new HjjsSwim();

        // Create a sample lesson
        Lesson lesson = new Lesson("Lesson 1", "Time 1", 90, "Coach 1");

        // Add the lesson using the addLesson method
        hjssSoftware.addLesson(lesson);

        // Retrieve the lessons list
        List<Lesson> lessons = hjssSoftware.getLessons();

        // Verify that the lesson is added correctly
        assertEquals(1, lessons.size());
        assertEquals(lesson, lessons.get(0));
        System.out.println("=========Test to Add Lesson Finished===========");
    }
    @Test
    public void testGetWeekRangeForMonth() {
        System.out.println("=========Test to Get Week Range Started===========");
        HjjsSwim calculator = new HjjsSwim();

        // Test valid month numbers
        assertEquals("1-4", calculator.get_week_range_for_month(1));
        assertEquals("5-8", calculator.get_week_range_for_month(2));
        assertEquals("9-12", calculator.get_week_range_for_month(3));
        assertEquals("13-16", calculator.get_week_range_for_month(4));
        assertEquals("17-20", calculator.get_week_range_for_month(5));
        assertEquals("21-24", calculator.get_week_range_for_month(6));
        assertEquals("25-28", calculator.get_week_range_for_month(7));
        assertEquals("29-32", calculator.get_week_range_for_month(8));
        assertEquals("33-36", calculator.get_week_range_for_month(9));
        assertEquals("37-40", calculator.get_week_range_for_month(10));
        assertEquals("41-44", calculator.get_week_range_for_month(11));
        assertEquals("45-48", calculator.get_week_range_for_month(12));

        // Test invalid month number
        assertEquals("Invalid month number", calculator.get_week_range_for_month(0));
        assertEquals("Invalid month number", calculator.get_week_range_for_month(13));
        System.out.println("=========Test to Get Week Range Finished===========");
    }
    
}
