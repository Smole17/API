package gloomyfolken.hooklib.minecraft;

import gloomyfolken.hooklib.asm.AsmHook;
import gloomyfolken.hooklib.asm.HookClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.DeobfuscationTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public abstract class HookLoader implements IFMLLoadingPlugin {

    private static DeobfuscationTransformer deobfuscationTransformer;

    public static HookClassTransformer getTransformer() {
        return PrimaryClassTransformer.instance.registeredSecondTransformer ? MinecraftClassTransformer.instance : PrimaryClassTransformer.instance;
    }

    public static void registerHook(final AsmHook hook) {
        getTransformer().registerHook(hook);
    }

    public static void registerHookContainer(final String className) {
        getTransformer().registerHookContainer(className);
    }

    static DeobfuscationTransformer getDeobfuscationTransformer() {
        if (HookLibPlugin.getObfuscated() && HookLoader.deobfuscationTransformer == null) {
            HookLoader.deobfuscationTransformer = new DeobfuscationTransformer();
        }
        return HookLoader.deobfuscationTransformer;
    }

    public String[] getLibraryRequestClass() {
        return null;
    }

    public String getAccessTransformerClass() {
        return null;
    }

    public String[] getASMTransformerClass() {
        return null;
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(final Map<String, Object> data) {
        this.registerHooks();
    }

    protected abstract void registerHooks();
}
