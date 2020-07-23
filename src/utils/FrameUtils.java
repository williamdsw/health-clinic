package utils;

import java.awt.Frame;
import javax.swing.JFrame;

/**
 *
 * @author William
 */
public class FrameUtils
{
    public static boolean isFrameAlreadyOpen (Class<?> currentClass) {
        for (Frame frame : Frame.getFrames ())
        {
            if (frame.getClass () == currentClass) {
                frame.setFocusable (true);
                frame.setVisible (true);
                return true;
            }
        }
        
        return false;
    }
}
