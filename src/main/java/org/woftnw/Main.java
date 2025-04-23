package org.woftnw;

import org.joml.Vector3f;
import java.io.IOException;

/**
 * Main class demonstrating the usage of MCRenderer.
 */
public class Main {
  public static void main(String[] args) {
    try {
      // Example URL to a Minecraft skin
      String skinUrl = "https://www.minecraftskins.com/uploads/skins/2025/04/23/ocean-waters---23219453.png?v825";

      // Output file path
      String outputPath = "rendered_skin.png";

      System.out.println("Starting Minecraft skin rendering from URL...");

      // Basic method - render with default settings
      boolean success = MCRenderer.renderModelFromUrl(skinUrl, outputPath);
      if (success) {
        System.out.println("Successfully rendered basic skin to: " + outputPath);
      } else {
        System.err.println("Failed to render basic skin");
      }

      // // Advanced method - render with custom configuration
      // String customOutputPath = "rendered_skin_custom.png";
      // RendererConfig config = new RendererConfig()
      // .setDimensions(500, 800)
      // .setBackgroundColor(0.2f, 0.3f, 0.4f)
      // .setModelRotationY(45.0f)
      // .setModelScale(1.2f)
      // .setLightPosition(5.0f, 15.0f, 40.0f)
      // .setModelTranslation(0.0f, -6.0f, 0.0f);

      // success = renderCustomSkin(skinUrl, customOutputPath, config);
      // if (success) {
      // System.out.println("Successfully rendered custom skin to: " +
      // customOutputPath);
      // } else {
      // System.err.println("Failed to render custom skin");
      // }

      System.out.println("Rendering complete");

    } catch (Exception e) {
      System.err.println("Error in main: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Helper method to render a Minecraft skin with custom configuration
   *
   * @param skinUrl    URL to the Minecraft skin texture
   * @param outputPath Path to save the rendered image
   * @param config     Renderer configuration
   * @return true if rendering was successful
   */
  private static boolean renderCustomSkin(String skinUrl, String outputPath, RendererConfig config) {
    try {
      // Get rendered image data
      java.nio.ByteBuffer imageData = MCRenderer.renderModelToBufferFromUrl(skinUrl, config);

      // Save to file
      return MCRenderer.saveImage(imageData, config.getWidth(), config.getHeight(), outputPath);
    } catch (IOException e) {
      System.err.println("Error rendering custom skin: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}
