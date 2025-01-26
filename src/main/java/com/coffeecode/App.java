package com.coffeecode;

import javax.swing.SwingUtilities;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
            } catch (Exception e) {
                log.error("Error starting application", e);
            }
        });
    }

}
