package ua.syt0r;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Utils {

    public static void log(String s){
        System.out.println(s);
    }

    public static BitmapFont generateFont(String fontPath, int size){
        FileHandle fontFile = Gdx.files.internal(fontPath);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = size;
        params.gamma = 0f;
        params.genMipMaps = true;
        BitmapFont f = generator.generateFont(params);
        generator.dispose();
        return f;
    }

}
