/*******************************************************************************
 * Copyright (c) 2008, 2010 Xuggle Inc.  All rights reserved.
 *  
 * This file is part of Xuggle-Utils.
 *
 * Xuggle-Utils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Xuggle-Utils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Xuggle-Utils.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package com.xuggle.utils;

import static org.junit.Assert.*;


import com.xuggle.test_utils.NameAwareTestClassRunner;
import com.xuggle.utils.Mutable;

import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RunWith(NameAwareTestClassRunner.class)
public class MutableTest
{
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private String mTestName = null;
  
  @Before
  public void setUp()
  {
    mTestName = NameAwareTestClassRunner.getTestMethodName();
    log.debug("-----START----- {}", mTestName);
  }
  @After
  public void tearDown()
  {
    log.debug("----- END ----- {}", mTestName);    
  }

  @Test
  public void testConstruction()
  {
    Mutable<Boolean> a = new Mutable<Boolean>(false);
    Mutable<Boolean> b = new Mutable<Boolean>();
    
    assertTrue(a.get() == false);
    assertTrue(b.get() == null);
  }
  
  @Test
  public void testSetting()
  {
    Mutable<Boolean> a = new Mutable<Boolean>(false);
    
    assertTrue(a.get() == false);
    a.set(true);
    assertTrue(a.get() == true);
  }
}
