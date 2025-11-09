package bosta.tests;

import bosta.auth.TokenManager;

public class DebugTokenTest {
    public static void main(String[] args) {
        String token = TokenManager.getInterviewToken();
        System.out.println("Token: " + token);
    }
}
