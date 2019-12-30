package com.drartgames.stepper;

public interface Version {
    int getMajor();

    int getMinor();

    int getIndex();

    int getBuildNumber();

    String getStringVersion();

    String getFullStringVersion();
}
