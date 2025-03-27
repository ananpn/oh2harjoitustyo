package com.oh2harjoitustyo;

@FunctionalInterface
public interface GameEventListener {
    void onGameOver(); // Called when player dies
}