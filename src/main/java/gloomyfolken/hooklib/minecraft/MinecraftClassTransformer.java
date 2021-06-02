package gloomyfolken.hooklib.minecraft;

import net.minecraft.launchwrapper.*;
import java.io.*;
import org.objectweb.asm.*;
import gloomyfolken.hooklib.asm.*;
import java.util.*;

public class MinecraftClassTransformer extends HookClassTransformer implements IClassTransformer
{
    static MinecraftClassTransformer instance;
    private Map<Integer, String> methodNames;
    private static List<IClassTransformer> postTransformers;

    public MinecraftClassTransformer() {
        MinecraftClassTransformer.instance = this;
        if (HookLibPlugin.getObfuscated()) {
            try {
                final long timeStart = System.currentTimeMillis();
                this.methodNames = this.loadMethodNames();
                final long time = System.currentTimeMillis() - timeStart;
                this.logger.debug("Methods dictionary loaded in " + time + " ms");
            }
            catch (IOException e) {
                this.logger.severe("Can not load obfuscated method names", e);
            }
        }
        this.hooksMap.putAll(PrimaryClassTransformer.instance.getHooksMap());
        PrimaryClassTransformer.instance.getHooksMap().clear();
        PrimaryClassTransformer.instance.registeredSecondTransformer = true;
    }

    private HashMap<Integer, String> loadMethodNames() throws IOException {
        final InputStream resourceStream = this.getClass().getResourceAsStream("/methods.bin");
        if (resourceStream == null) {
            throw new IOException("Methods dictionary not found");
        }
        final DataInputStream input = new DataInputStream(new BufferedInputStream(resourceStream));
        final int numMethods = input.readInt();
        final HashMap<Integer, String> map = new HashMap<Integer, String>(numMethods);
        for (int i = 0; i < numMethods; ++i) {
            map.put(input.readInt(), input.readUTF());
        }
        input.close();
        return map;
    }

    public byte[] transform(final String oldName, final String newName, byte[] bytecode) {
        bytecode = this.transform(newName, bytecode);
        for (int i = 0; i < MinecraftClassTransformer.postTransformers.size(); ++i) {
            bytecode = MinecraftClassTransformer.postTransformers.get(i).transform(oldName, newName, bytecode);
        }
        return bytecode;
    }

    @Override
    protected HookInjectorClassVisitor createInjectorClassVisitor(final ClassWriter cw, final List<AsmHook> hooks) {
        return new HookInjectorClassVisitor(cw, hooks) {
            @Override
            protected boolean isTargetMethod(final AsmHook hook, final String name, final String desc) {
                if (HookLibPlugin.getObfuscated()) {
                    final String mcpName = MinecraftClassTransformer.this.methodNames.get(MinecraftClassTransformer.getMethodId(name));
                    if (mcpName != null && super.isTargetMethod(hook, mcpName, desc)) {
                        return true;
                    }
                }
                return super.isTargetMethod(hook, name, desc);
            }
        };
    }

    public Map<Integer, String> getMethodNames() {
        return this.methodNames;
    }

    public static int getMethodId(final String srgName) {
        if (srgName.startsWith("func_")) {
            final int first = srgName.indexOf(95);
            final int second = srgName.indexOf(95, first + 1);
            return Integer.valueOf(srgName.substring(first + 1, second));
        }
        return -1;
    }

    public static void registerPostTransformer(final IClassTransformer transformer) {
        MinecraftClassTransformer.postTransformers.add(transformer);
    }

    static {
        MinecraftClassTransformer.postTransformers = new ArrayList<IClassTransformer>();
    }
}
