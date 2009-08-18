/*
 * Copyright (c) 2008, 2009 by Xuggle Incorporated.  All rights reserved.
 * 
 * This file is part of Xuggler.
 * 
 * You can redistribute Xuggler and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Xuggler is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with Xuggler.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xuggle.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * This class is a wrapper for a time value (long) and a {@link TimeUnit}.
 * <p>
 * It allows you to always know what {@link TimeUnit} a given value is
 * expressed in.
 * </p>
 * <p>
 * This is useful for wrapping durations (e.g. 15 milliseconds) or
 * time stamp values (e.g. "a Presentation Time Stamp of 22050 expressed
 * in {@link TimeUnit#MICROSECONDS}
 * </p>
 * @author aclarke
 *
 */
public final class TimeValue implements Comparable<TimeValue>
{
  private long mValue;
  private TimeUnit mUnit;

  public TimeValue()
  {
    setValue(0);
    setUnit(TimeUnit.MILLISECONDS);
  }
  
  public TimeValue(long aValue, TimeUnit aUnit)
  {
    setValue(aValue);
    setUnit(aUnit);
  }
  
  public TimeValue(long value, String unit)
  {
    this(value, TimeUnit.valueOf(unit));
  }
 
  /**
   * Get current time, as returned by {@link System#nanoTime()}.
   * 
   * @return current time
   */
  
  public static TimeValue nanoNow()
  {
    return new TimeValue(System.nanoTime(), TimeUnit.NANOSECONDS);
  }
  
  /**
   * Get current time, as returned by {@link System#currentTimeMillis()}.
   * 
   * @return current time
   */
  
  public static TimeValue now()
  {
    return new TimeValue(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
  }
  
  public TimeValue(TimeValue aSrc)
  {
    if (aSrc == null)
      throw new IllegalArgumentException("must pass valid time value");
    setValue(aSrc.getValue());
    setUnit(aSrc.getUnit());
  }

  public long get(TimeUnit aUnit)
  {
    if (aUnit == null)
      throw new IllegalArgumentException("must pass valid TimeUnit");
    
    return aUnit.convert(getValue(), getUnit());
  }
  
  public TimeUnit getTimeUnit()
  {
    return getUnit();
  }
  
  public TimeValue copy()
  {
    return new TimeValue(getValue(), getUnit());
  }
  
  public int compareTo(TimeValue that)
  {
    if (this == that)
      return 0;
    
    if (that == null)
      throw new NullPointerException();
    
    // converts down to lowest granularity.  This will fail miserably for
    // large durations, but right now we're doing video, and durations rarely
    // exceed the Long.MAX_VALUE Nanoseconds
    
    // IMPORTANT: Be very careful if you try to "fix" this; the current implementation,
    // while treating any durations about Long.MAX_VALUE Nanoseconds, has the
    // correct semantics for compareTo(Object), equals(Object) and hashCode();
   
    // See http://www.javaworld.com/javaworld/jw-01-1999/jw-01-object.html?page=4 if
    // you want to know why that's important
   
    TimeUnit minUnit = getUnit();
    
    if (that.getUnit().ordinal() < minUnit.ordinal())
      minUnit = that.getUnit();
    
    long thisNs = minUnit.convert(this.getValue(), this.getUnit());
    long thatNs = minUnit.convert(that.getValue(), that.getUnit());
    
    final long maxDistance = Long.MAX_VALUE / 2;
    int adjustment = 1;
    if ((thisNs > maxDistance && thatNs <= -maxDistance) ||
        (thatNs > maxDistance && thisNs <= -maxDistance))
      adjustment = -1;
    return thisNs < thatNs ? -adjustment : (thisNs > thatNs ? adjustment : 0);
  }
  
  @Override
  public boolean equals(Object that)
  {
    if (this == that)
      return true;
    
    if (that != null && (that.getClass().equals(this.getClass())))
    {
      return this.compareTo((TimeValue)that)==0;
    }
    else
      return false;
  }
  
  @Override
  public int hashCode()
  {
    return (int)TimeUnit.NANOSECONDS.convert(getValue(), getUnit());
  }
  
  /**
   * Returns a string value, as a decimal number of seconds.
   * 
   */
  @Override
  public String toString()
  {
    NumberFormat format = new DecimalFormat("###,###,###,###,###,###,###");
    String s = format.format(getValue());
    return s + " (" + getUnit() + ")";
  }

  /**
   * Sets the raw value; Should only be used by Bean factories.
   * {@link TimeValue} objects are meant to be immutable once
   * created.
   * @param value the value to set
   */
  public void setValue(long value)
  {
    mValue = value;
  }

  /**
   * Gets the raw value; Should only be used by Bean factories.
   * @return the value
   */
  public long getValue()
  {
    return mValue;
  }

  /**
   * Sets the raw unit; Should only be used by Bean factories.
   * {@link TimeValue} objects are meant to be immutable once
   * created.
   * @param unit the unit to set
   */
  public void setUnit(TimeUnit unit)
  {
    mUnit = unit;
  }

  /**
   * Gets the raw unit; Should only be used by Bean factories.
   * @return the unit
   */
  public TimeUnit getUnit()
  {
    return mUnit;
  }
}
