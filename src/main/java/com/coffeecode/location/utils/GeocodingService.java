// package com.coffeecode.location.utils;

// import java.io.IOException;

// import com.coffeecode.location.elevations.impl.ApiClient;
// import com.coffeecode.location.utils.exception.GeoserviceException;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

// public class GeocodingService {

// private static final ObjectMapper objectMapper = new ObjectMapper();

// // Method untuk mendapatkan nama lokasi berdasarkan latitude dan longitude
// public static String getLocationName(double latitude, double longitude)
// throws GeoserviceException {
// // Endpoint untuk Nominatim API
// String url = "https://nominatim.openstreetmap.org/reverse?lat=" + latitude +
// "&lon=" + longitude
// + "&format=json&addressdetails=1";

// // Mengirimkan permintaan HTTP GET
// String response;
// try {
// response = ApiClient.sendGetRequest(url);
// } catch (GeoserviceException e) {
// throw new GeoserviceException("Failed to get location name.", e);
// }

// // Parsing JSON response untuk mendapatkan nama lokasi
// try {
// JsonNode jsonResponse = objectMapper.readTree(response);
// return jsonResponse.get("display_name").asText(); // Nama lengkap lokasi
// } catch (IOException e) {
// throw new GeoserviceException("Failed to parse location name.", e);
// }
// }

// private GeocodingService() {
// throw new IllegalStateException("Utility class");
// }
// }
