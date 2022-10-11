package org.example;

import org.example.View.GuiState;

public class AppState {
    private GuiState state = GuiState.Default;

    public void setState(GuiState state) {
        this.state = state;
    }

    public GuiState getState() {
        return state;
    }
}
