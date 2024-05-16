import processing.core.PApplet;

public class Sketch extends PApplet {

  // Related arrays for the x and y coords of snow and snow variables
  float[] snowX = new float[42];
  float[] snowY = new float[42];
  boolean[] ballHideStatus = new boolean[42];
  int snowDiameter = 10;
  int snowSpeed = 3;

  // Variables for key pressing
  boolean upPressed = false;
  boolean downPressed = false;

  // player circle variables
  float circleX = 200;
  float circleY = 350;
  int circleSize = 30;

  // Lives variable
  int numLives = 3;

  public void settings() {
    size(400, 400);
  }

  public void setup() {
    // Make background
    background(0);

    // Make random snow x and y values
    for (int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
      snowY[i] = random(height);
      ballHideStatus[i] = false;
    }
  }

  public void draw() {
    // Make backgorund white if lives lost
    if (numLives == 0) {
      background(255);
    }
    else {
      background(0);
      // Draw snow
      snow();

      // Player
      player();
  
      // Draw lives
      playerLives();
    }
  }
  
  /**
   * This method allows for snowfall that can speed and slow down and dissapear on click.
   * @author: Anton Wong
   */
  public void snow() {
    for (int i = 0; i < snowX.length; i++) {
      if (!ballHideStatus[i]) {
        // Draw snow
        fill(255);
        circle(snowX[i], snowY[i], snowDiameter);

        // Slow or speed up snow fall on key press
        if (upPressed) {
          snowSpeed = 1;
        }
        else if (downPressed) {
          snowSpeed = 5;
        }
        else {
          snowSpeed = 3;
        }

        // Make snow fall
        snowY[i] += snowSpeed;

        // Reset snow
        if (snowY[i] > height) {
          snowY[i] = 0;
        }

        // Collision detection with player
        if (dist(snowX[i], snowY[i], circleX, circleY) < snowDiameter / 10 * circleSize / 2) {
          snowY[i] = 0;
          numLives--;
        }

        // Collision with mouse pressed
        if (mousePressed) {
          if(dist(snowX[i], snowY[i], mouseX, mouseY) < snowDiameter / 2) {
            ballHideStatus[i] = true;
          }
        }
      }
    }
  }

  /**
   * This method is for the blue square which is the player that can be controlled by "WASD" to move.
   * @author: Anton Wong
   */
  public void player() {
    fill(0, 0, 255);
    circle(circleX, circleY, circleSize);

    // WASD to move player
    if (keyPressed) {
      if (key == 'w') {
        circleY -= 2;
      }
      else if (key == 's') {
        circleY += 2;
      }
      else if (key == 'a') {
        circleX -= 2;
      }
      else if (key == 'd') {
        circleX += 2;
      }
    }
  }

  /**
   * This method allows for three lives as red squares to be displayed on the screen and the player loses a life each collison with snow.
   * @author: Anton Wong
   */
  public void playerLives() {
    // Make three lives
    fill(255, 0, 0);
    for (int i = numLives; i > 0; i--) {
      square(width - (i * 50), 50, 40);
    }
  }

  public void keyPressed() {
    // Check when specific keycode pressed down
    if (keyCode == UP) {
      upPressed = true;
    }
    if (keyCode == DOWN) {
      downPressed = true;
    }
  }

  public void keyReleased() {
    // Check when key released
    if (keyCode == UP) {
      upPressed = false;
    }
    if (keyCode == DOWN) {
      downPressed = false;
    }
  }
}