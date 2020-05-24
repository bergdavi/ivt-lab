package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryMock;
  private TorpedoStore secondaryMock;


  @BeforeEach
  public void init(){
    primaryMock = Mockito.mock(TorpedoStore.class);
    secondaryMock = Mockito.mock(TorpedoStore.class);

    this.ship = new GT4500(primaryMock, secondaryMock);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryMock.fire(anyInt())).thenReturn(true);
    when(secondaryMock.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryMock, times(1)).fire(anyInt());
    verify(secondaryMock, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryMock.fire(anyInt())).thenReturn(true);
    when(secondaryMock.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryMock, times(1)).fire(anyInt());
    verify(secondaryMock, times(1)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_Fail() {
    // Arrange
    when(primaryMock.fire(anyInt())).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryMock, times(1)).fire(anyInt());
    verify(secondaryMock, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_PrimaryEmpty() {
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryMock, never()).fire(anyInt());
    verify(secondaryMock, times(1)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_2times_Success() {
    // Arrange
    when(primaryMock.fire(anyInt())).thenReturn(true);
    when(secondaryMock.fire(anyInt())).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(primaryMock, times(1)).fire(anyInt());
    verify(secondaryMock, times(1)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_2timesPrimaryEmpty_Success() {
    // Arrange
    when(primaryMock.fire(anyInt())).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(primaryMock, times(2)).fire(anyInt());
    verify(secondaryMock, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_PrimaryFail() {
    // Arrange
    when(primaryMock.fire(anyInt())).thenReturn(false);
    when(secondaryMock.fire(anyInt())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryMock, times(1)).fire(anyInt());
    verify(secondaryMock, times(1)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_BothEmpty() {
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryMock, never()).fire(anyInt());
    verify(secondaryMock, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_Single_PrimaryFiredLast_PrimaryEmpty() {
    // Arrange
    when(primaryMock.fire(anyInt())).thenReturn(true);
    when(primaryMock.isEmpty()).thenReturn(false, true);
    when(secondaryMock.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result1);
    verify(primaryMock, times(1)).fire(anyInt());
    verify(secondaryMock, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_BothEmpty() {
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primaryMock, never()).fire(anyInt());
    verify(secondaryMock, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_All_BothFail() {
    // Arrange
    when(primaryMock.fire(anyInt())).thenReturn(false);
    when(secondaryMock.fire(anyInt())).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primaryMock, times(1)).fire(anyInt());
    verify(secondaryMock, times(1)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_None() {
      // Act
      boolean result = ship.fireTorpedo(FiringMode.NONE);

      // Assert
      assertEquals(false, result);
  }

}
