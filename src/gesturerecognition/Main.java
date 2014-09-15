package gesturerecognition;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import nn.Network;
import utils.ImageUtils;



public class Main {

	public static void main(String[] args) {
		try {
            new Video();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
