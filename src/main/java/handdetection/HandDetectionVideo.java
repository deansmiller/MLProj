package handdetection;

import webcam.Video;


/**
 * User: deansmiller
 * Date: 17/09/14
 * Time: 16:28
 */


public class HandDetectionVideo {


    public static void main(String... args){

        try {
            new Video(new SkinFilterer(320, 240));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
