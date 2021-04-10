package org.quantumclient.qubit.mangers;

import com.google.gson.internal.LinkedHashTreeMap;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.Setting;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ConfigManger {

    private static final String MAIN_FOLDER = "Qubit/";
    private static final String MODULE_FOLDER = "Modules/";
    private final Yaml yaml;

    public ConfigManger() {
        DumperOptions options = new DumperOptions();
        options.setIndent(4);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);
    }

    public void init() {
        makeDir();
        load();
        save();
    }

    public void load() {
        try {
            loadModules();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            saveModules();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveModules() throws IOException {
        Map<String, Object> dataMap = new LinkedHashTreeMap<>();
        for (Module module : Qubit.getModuleManger().getModules()) {
            String moduleLocation = MODULE_FOLDER + StringUtils.capitalize(module.getCategory().name()) + "/";
            makeFile(moduleLocation, module.getName());
            dataMap.put("Name", module.getName());
            dataMap.put("Toggled", module.isToggled());
            dataMap.put("Bind", module.getBind());
            if (module.hasSetting()) {
                Map<String, Object> settingMap = new HashMap<>();
                for (Setting setting : module.getSettingList()) {
                    settingMap.put(setting.getName(), setting.getValue());
                }
                dataMap.put("Settings", settingMap);
            }
            PrintWriter writer = new PrintWriter(MAIN_FOLDER + moduleLocation + module.getName() + ".yml");
            yaml.dump(dataMap, writer);
            dataMap.clear();
        }
    }

    private void loadModules() throws IOException {
        for (Module module : Qubit.getModuleManger().getModules()) {
            String moduleLocation = MAIN_FOLDER + MODULE_FOLDER + StringUtils.capitalize(module.getCategory().name().toLowerCase()) + "/";
            if (!Files.exists(Paths.get(moduleLocation + module.getName() + ".yml"))) continue;
            InputStream inputStream = Files.newInputStream(Paths.get(moduleLocation + module.getName() + ".yml"));
            Map<String, Object> data = yaml.load(inputStream);
            if (data == null || data.get("Name") == null) continue;
            module.setToggled((Boolean) data.get("Toggled"));
            if (module.hasSetting()) {
                for (Setting setting : module.getSettingList()) {
                    Map<String, Object> settingMap = (Map<String, Object>) data.get("Settings");
                    if (settingMap.containsKey(setting.getName())) setting.setValue(settingMap.get(setting.getName()));
                }
            }
            inputStream.close();
        }
    }


    private void makeFile(@Nullable String location, String name) throws IOException {
        if (location != null) {
            if (!Files.exists(Paths.get(MAIN_FOLDER + location + name + ".yml"))) {
                Files.createFile(Paths.get(MAIN_FOLDER + location + name + ".yml"));
            }
        } else {
            if (!Files.exists(Paths.get(MAIN_FOLDER + name + ".yml"))) {
                Files.createFile(Paths.get(MAIN_FOLDER + name + ".yml"));
            }
        }
    }

    private void makeDir() {
        try {
            if (!Files.exists(Paths.get(MAIN_FOLDER))) {
                Files.createDirectories(Paths.get(MAIN_FOLDER));
            }
            if (!Files.exists(Paths.get(MAIN_FOLDER + MODULE_FOLDER))) {
                Files.createDirectories(Paths.get(MAIN_FOLDER + MODULE_FOLDER));
            }
            for (Category category : Category.values()) {
                String dir = MAIN_FOLDER + MODULE_FOLDER + StringUtils.capitalize(category.name().toLowerCase()) + "/";
                if (!Files.exists(Paths.get(dir))) {
                    Files.createDirectories(Paths.get(dir));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
