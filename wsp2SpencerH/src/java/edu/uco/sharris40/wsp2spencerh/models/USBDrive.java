package edu.uco.sharris40.wsp2spencerh.models;

public class USBDrive extends Item {
  private static final long serialVersionUID = 1L;
  private static final String NAME = "USB Drive";

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public int getCost() {
    return 220;
  }

}
