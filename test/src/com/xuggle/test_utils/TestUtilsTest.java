package com.xuggle.test_utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUtilsTest
{

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void testGetNameOfCallingMethod()
  {
    assertEquals("testGetNameOfCallingMethod",
        TestUtils.getNameOfCallingMethod());
  }

}
