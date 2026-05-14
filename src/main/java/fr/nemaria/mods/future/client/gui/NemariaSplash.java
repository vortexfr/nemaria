package fr.nemaria.mods.future.client.gui;

import cpw.mods.fml.common.EnhancedRuntimeException;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ICrashCallable;
import cpw.mods.fml.common.ProgressManager;
import cpw.mods.fml.common.asm.FMLSanityChecker;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.SharedDrawable;
import org.lwjgl.util.glu.GLU;

public class NemariaSplash {
    // États du thread
    private static Drawable d;
    private static volatile boolean pause = false;
    private static volatile boolean done = false;
    private static Thread thread;
    private static volatile Throwable threadError;
    private static final Lock lock = new ReentrantLock(true);
    
    // Renderers et ressources
    private static SplashFontRenderer fontRenderer;
    private static final IResourcePack mcPack = Minecraft.getMinecraft().mcDefaultResourcePack;
    private static final IResourcePack fmlPack = createResourcePack(FMLSanityChecker.fmlLocation);
    private static IResourcePack miscPack;
    
    // Textures
    private static Texture fontTexture;
    private static Texture backgroundTexture;
    
    // Configuration
    private static Properties config;
    private static boolean enabled;
    private static int backgroundColor;
    private static int fontColor;
    private static int barColor;
    private static int barBackgroundColor;
    
    // Tips system
    private static String[] tipsList = new String[] {
        "Bienvenue sur Nemaria V6 !",
        "Explore un monde plein de mystères et d'aventures !",
        "N'oublie pas de consulter le wiki pour les recettes de craft !",
        "Rejoins notre Discord pour rester informé des nouveautés !",
        "Profite de l'expérience Nemaria !"
    };
    private static String currentTip;
    
    // Semaphore pour la synchronisation
    public static final Semaphore mutex = new Semaphore(1);
    private static final IntBuffer buf = BufferUtils.createIntBuffer(4194304);
    
    // Méthodes utilitaires de configuration
    private static String getString(String name, String def) {
        String value = config.getProperty(name, def);
        config.setProperty(name, value);
        return value;
    }
    
    private static boolean getBool(String name, boolean def) {
        return Boolean.parseBoolean(getString(name, Boolean.toString(def)));
    }
    
    private static int getHex(String name, int def) {
        return Integer.decode(getString(name, "0x" + Integer.toString(def, 16).toUpperCase())).intValue();
    }
    
    // Méthode principale de démarrage
    public static void start() {
        try {
            FMLLog.info("[Nemaria Splash] Starting splash screen...", new Object[0]);
            
            // Sélection d'un tip aléatoire
            currentTip = tipsList[new Random().nextInt(tipsList.length)];
            
            // Chargement de la configuration
            File configFile = new File(Minecraft.getMinecraft().mcDataDir, "config/nemaria-splash.properties");
            FileReader r = null;
            config = new Properties();
            
            try {
                r = new FileReader(configFile);
                config.load(r);
                FMLLog.info("[Nemaria Splash] Configuration loaded", new Object[0]);
            } catch (IOException e) {
                FMLLog.info("[Nemaria Splash] Could not load nemaria-splash.properties, will create a default one", new Object[0]);
            } finally {
                IOUtils.closeQuietly(r);
            }
            
            // Configuration des valeurs
            if (!configFile.exists()) {
                enabled = true;
                FMLLog.info("[Nemaria Splash] First launch, enabling splash by default", new Object[0]);
            } else {
                enabled = getBool("enabled", true);
            }
            
            if (!enabled) {
                FMLLog.info("[Nemaria Splash] Splash is disabled, skipping", new Object[0]);
                return;
            }
            
            backgroundColor = getHex("background", 0x1a1a2e);
            fontColor = getHex("font", 0xFFFFFF);
            barColor = getHex("barColor", 0x00d4ff);
            barBackgroundColor = getHex("barBackground", 0x16213e);
            
            // Chemins des ressources
            final ResourceLocation fontLoc = new ResourceLocation(getString("fontTexture", "textures/font/ascii.png"));
            final ResourceLocation backgroundLoc = new ResourceLocation("nemaria", "textures/splash/background.png");
            
            File miscPackFile = new File(Minecraft.getMinecraft().mcDataDir, getString("resourcePackPath", "resources"));
            
            // Sauvegarde de la configuration
            FileWriter w = null;
            try {
                w = new FileWriter(configFile);
                config.store(w, "Nemaria Splash screen properties");
            } catch (IOException e) {
                FMLLog.log(Level.ERROR, e, "Could not save the nemaria-splash.properties file", new Object[0]);
            } finally {
                IOUtils.closeQuietly(w);
            }
            
            miscPack = createResourcePack(miscPackFile);
            
            // Enregistrement des informations de crash
            FMLCommonHandler.instance().registerCrashCallable(new ICrashCallable() {
                public String call() throws Exception {
                    return "' Vendor: '" + GL11.glGetString(GL11.GL_VENDOR) + 
                           "' Version: '" + GL11.glGetString(GL11.GL_VERSION) + 
                           "' Renderer: '" + GL11.glGetString(GL11.GL_RENDERER) + "'";
                }
                public String getLabel() {
                    return "GL info";
                }
            });
            
            // Initialisation OpenGL
            try {
                d = new SharedDrawable(Display.getDrawable());
                Display.getDrawable().releaseContext();
                d.makeCurrent();
            } catch (LWJGLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            
            // Création du thread de rendu
            thread = new Thread(new Runnable() {
                private float progress = 0.0F;
                private float targetProgress = 0.0F;
                private int barWidth = 0;
                private int barHeight = 0;
                
                public void run() {
                    try {
                        setGL();
                        
                        // Chargement des textures
                        fontTexture = new Texture(fontLoc);
                        backgroundTexture = new Texture(backgroundLoc);
                    
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    fontRenderer = new SplashFontRenderer();
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    
                    while (!done) {
                        ProgressManager.ProgressBar first = null;
                        ProgressManager.ProgressBar last = null;
                        Iterator<ProgressManager.ProgressBar> i = ProgressManager.barIterator();
                        
                        while (i.hasNext()) {
                            if (first == null) {
                                first = i.next();
                                continue;
                            }
                            last = i.next();
                        }
                        
                        // Clear screen
                        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                        
                        int w = Display.getWidth();
                        int h = Display.getHeight();
                        barWidth = w - 40;
                        barHeight = (int)(h * 0.017F);
                        
                        // Setup matrices
                        GL11.glViewport(0, 0, w, h);
                        GL11.glMatrixMode(GL11.GL_PROJECTION);
                        GL11.glLoadIdentity();
                        GL11.glOrtho(320.0 - w / 2, 320.0 + w / 2, 240.0 + h / 2, 240.0 - h / 2, -1.0, 1.0);
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        GL11.glLoadIdentity();
                        
                        // Dessiner le background en plein écran
                        try {
                            setColor(Color.WHITE.getRGB());
                            GL11.glEnable(GL11.GL_TEXTURE_2D);
                            backgroundTexture.bind();
                            GL11.glBegin(GL11.GL_QUADS);
                            backgroundTexture.texCoord(0, 0.0F, 0.0F);
                            GL11.glVertex2f(320 - w / 2, 240 - h / 2);
                            backgroundTexture.texCoord(0, 0.0F, 1.0F);
                            GL11.glVertex2f(320 - w / 2, 240 + h / 2);
                            backgroundTexture.texCoord(0, 1.0F, 1.0F);
                            GL11.glVertex2f(320 + w / 2, 240 + h / 2);
                            backgroundTexture.texCoord(0, 1.0F, 0.0F);
                            GL11.glVertex2f(320 + w / 2, 240 - h / 2);
                            GL11.glEnd();
                            GL11.glDisable(GL11.GL_TEXTURE_2D);
                        } catch (Exception e) {
                            // Erreur background
                        }
                        
                        // Afficher la barre de chargement en bas
                        if (first != null) {
                            targetProgress = (float)(first.getStep() + 1) / (float)(first.getSteps() + 1);
                            progress = smoothValue(progress, targetProgress);
                            
                            GL11.glPushMatrix();
                            GL11.glTranslatef(320 - w / 2 + 20, 240 + h / 2 - h * 0.10F + 5, 0);
                            
                            // Fond de la barre
                            setColor(barBackgroundColor);
                            drawBox(barWidth, barHeight);
                            
                            // Barre de progression
                            setColor(barColor);
                            GL11.glTranslatef(2, 2, 0);
                            drawBox((int)((barWidth - 4) * progress), barHeight - 4);
                            
                            GL11.glPopMatrix();
                        }
                        
                        // Synchronisation et update
                        mutex.acquireUninterruptibly();
                        Display.update();
                        mutex.release();
                        
                        if (pause) {
                            clearGL();
                            setGL();
                        }
                        
                        Display.sync(100);
                    }
                    
                    clearGL();
                    } catch (Throwable t) {
                        FMLLog.log(Level.ERROR, t, "[Nemaria Splash] Error in render thread", new Object[0]);
                        threadError = t;
                    }
                }
                
                private void setColor(int color) {
                    GL11.glColor3ub(
                        (byte)(color >> 16 & 0xFF), 
                        (byte)(color >> 8 & 0xFF), 
                        (byte)(color & 0xFF)
                    );
                }
                
                private void drawBox(float w, float h) {
                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex2f(0.0F, 0.0F);
                    GL11.glVertex2f(0.0F, h);
                    GL11.glVertex2f(w, h);
                    GL11.glVertex2f(w, 0.0F);
                    GL11.glEnd();
                }
                
                private float smoothValue(float value, float target) {
                    if (value >= target) {
                        return target;
                    }
                    float diff = target - value;
                    float offset = 0.075F * diff / 3.0F;
                    if (diff <= 0.075F) {
                        return target;
                    }
                    value += offset;
                    return value;
                }
                
                private void setGL() {
                    lock.lock();
                    try {
                        Display.getDrawable().makeCurrent();
                    } catch (LWJGLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    
                    GL11.glClearColor(
                        (backgroundColor >> 16 & 0xFF) / 255.0F,
                        (backgroundColor >> 8 & 0xFF) / 255.0F,
                        (backgroundColor & 0xFF) / 255.0F,
                        1.0F
                    );
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                }
                
                private void clearGL() {
                    Minecraft mc = Minecraft.getMinecraft();
                    mc.displayWidth = Display.getWidth();
                    mc.displayHeight = Display.getHeight();
                    mc.resize(mc.displayWidth, mc.displayHeight);
                    
                    GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                    GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                    
                    try {
                        Display.getDrawable().releaseContext();
                    } catch (LWJGLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    } finally {
                        lock.unlock();
                    }
                }
            });
            
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(Thread t, Throwable e) {
                    FMLLog.log(Level.ERROR, e, "Nemaria Splash thread Exception", new Object[0]);
                    threadError = e;
                }
            });
            
            thread.start();
            checkThreadState();
            
        } catch (Throwable t) {
            FMLLog.log(Level.ERROR, t, "[Nemaria Splash] Fatal error starting splash screen, disabling it", new Object[0]);
            enabled = false;
            threadError = t;
        }
    }
    
    private static void checkThreadState() {
        if (thread.getState() == Thread.State.TERMINATED || threadError != null) {
            throw new IllegalStateException("Splash thread", threadError);
        }
    }
    
    @Deprecated
    public static void pause() {
        if (!enabled) return;
        checkThreadState();
        pause = true;
        lock.lock();
        try {
            d.releaseContext();
            Display.getDrawable().makeCurrent();
        } catch (LWJGLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Deprecated
    public static void resume() {
        if (!enabled) return;
        checkThreadState();
        pause = false;
        try {
            Display.getDrawable().releaseContext();
            d.makeCurrent();
        } catch (LWJGLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        lock.unlock();
    }
    
    public static void finish() {
        if (!enabled) return;
        try {
            checkThreadState();
            done = true;
            thread.join();
            d.releaseContext();
            Display.getDrawable().makeCurrent();
            fontTexture.delete();
            backgroundTexture.delete();
        } catch (Exception e) {
            e.printStackTrace();
            if (disableSplash()) {
                throw new EnhancedRuntimeException(e) {
                    protected void printStackTrace(EnhancedRuntimeException.WrappedPrintStream stream) {
                        stream.println("Nemaria SplashProgress error.");
                    }
                };
            }
        }
    }
    
    private static boolean disableSplash() {
        File configFile = new File(Minecraft.getMinecraft().mcDataDir, "config/nemaria-splash.properties");
        File parent = configFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        enabled = false;
        config.setProperty("enabled", "false");
        FileWriter w = null;
        try {
            w = new FileWriter(configFile);
            config.store(w, "Nemaria Splash screen properties");
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            IOUtils.closeQuietly(w);
        }
    }
    
    private static IResourcePack createResourcePack(File file) {
        if (file.isDirectory()) {
            return new FolderResourcePack(file);
        }
        return new FileResourcePack(file);
    }
    
    public static void drawVanillaScreen() throws LWJGLException {
        if (!enabled) {
            Minecraft.getMinecraft().loadScreen();
        }
    }
    
    public static void clearVanillaResources(TextureManager renderEngine, ResourceLocation mojangLogo) {
        if (!enabled) {
            renderEngine.deleteTexture(mojangLogo);
        }
    }
    
    public static void checkGLError(String where) {
        int err = GL11.glGetError();
        if (err != 0) {
            throw new IllegalStateException(where + ": " + GLU.gluErrorString(err));
        }
    }
    
    private static InputStream open(ResourceLocation loc) throws IOException {
        try {
            if (miscPack != null && miscPack.resourceExists(loc)) {
                return miscPack.getInputStream(loc);
            }
            if (fmlPack != null && fmlPack.resourceExists(loc)) {
                return fmlPack.getInputStream(loc);
            }
            return mcPack.getInputStream(loc);
        } catch (IOException e) {
            throw e;
        }
    }
    
    private static class Texture {
        private final ResourceLocation location;
        private final int name;
        private final int width;
        private final int height;
        private final int frames;
        private final int size;
        
        public Texture(ResourceLocation location) {
            InputStream s = null;
            try {
                this.location = location;
                s = open(location);
                
                if (s == null) {
                    throw new IOException("Resource not found: " + location);
                }
                
                ImageInputStream stream = ImageIO.createImageInputStream(s);
                Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
                
                if (!readers.hasNext()) {
                    throw new IOException("No suitable reader");
                }
                
                ImageReader reader = readers.next();
                reader.setInput(stream);
                this.frames = reader.getNumImages(true);
                BufferedImage[] images = new BufferedImage[this.frames];
                
                for (int i = 0; i < this.frames; i++) {
                    images[i] = reader.read(i);
                }
                reader.dispose();
                
                int size = 1;
                this.width = images[0].getWidth();
                this.height = images[0].getHeight();
                
                while (size / this.width * (size / this.height) < this.frames) {
                    size *= 2;
                }
                this.size = size;
                
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                synchronized (NemariaSplash.class) {
                    this.name = GL11.glGenTextures();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.name);
                }
                
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, 6408, size, size, 0, 
                                 32993, 33639, (IntBuffer)null);
                
                for (int i = 0; i * (size / this.width) < this.frames; i++) {
                    for (int j = 0; i * (size / this.width) + j < this.frames && j < size / this.width; j++) {
                        buf.clear();
                        BufferedImage image = images[i * (size / this.width) + j];
                        
                        for (int k = 0; k < this.height; k++) {
                            for (int l = 0; l < this.width; l++) {
                                buf.put(image.getRGB(l, k));
                            }
                        }
                        
                        buf.position(0).limit(this.width * this.height);
                        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, j * this.width, i * this.height, 
                                            this.width, this.height, 32993, 
                                            33639, buf);
                    }
                }
                
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(s);
            }
        }
        
        public ResourceLocation getLocation() { return this.location; }
        public void bind() { GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.name); }
        public void delete() { GL11.glDeleteTextures(this.name); }
        public float getU(int frame, float u) { return (float)this.width * ((float)(frame % (this.size / this.width)) + u) / (float)this.size; }
        public float getV(int frame, float v) { return (float)this.height * ((float)(frame / (this.size / this.width)) + v) / (float)this.size; }
        public void texCoord(int frame, float u, float v) { GL11.glTexCoord2f(getU(frame, u), getV(frame, v)); }
    }
    
    private static class SplashFontRenderer extends FontRenderer {
        public SplashFontRenderer() {
            super(Minecraft.getMinecraft().gameSettings, fontTexture.getLocation(), null, false);
            super.onResourceManagerReload(null);
        }
        protected void bindTexture(ResourceLocation location) {
            if (location != this.locationFontTexture) {
                throw new IllegalArgumentException();
            }
            fontTexture.bind();
        }
        protected InputStream getResourceInputStream(ResourceLocation location) throws IOException {
            return Minecraft.getMinecraft().mcDefaultResourcePack.getInputStream(location);
        }
    }
}
