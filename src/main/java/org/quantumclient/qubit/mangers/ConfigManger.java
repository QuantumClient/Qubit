package org.quantumclient.qubit.mangers;

import com.google.gson.internal.LinkedHashTreeMap;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.gui.click.ClickGuiScreen;
import org.quantumclient.qubit.gui.click.Frame;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.Setting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
            loadFriends();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            saveModules();
            saveFriends();
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
                Map<String, Object> settingMap = new LinkedHashTreeMap<>();
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
            module.setToggled((boolean) data.get("Toggled"));
            if (module.hasSetting() && data.containsKey("Settings")) {
                Map<String, Object> settingMap = (Map<String, Object>) data.get("Settings");
                for (Setting setting : module.getSettingList()) {
                    if (settingMap.containsKey(setting.getName())) {
                        if (setting instanceof FloatSetting) {
                            setting.setValue(((Double) settingMap.get(setting.getName())).floatValue()); /*Yaml seems to load floats as doubles no matter what i do so this is have to do */
                        } else {
                            setting.setValue(settingMap.get(setting.getName()));
                        }
                    }
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

    public void loadGui(ClickGuiScreen clickGuiScreen) {
        try {
            if (!Files.exists(Paths.get(MAIN_FOLDER + "Gui.yml"))) return;
            InputStream inputStream = Files.newInputStream(Paths.get(MAIN_FOLDER + "Gui.yml"));
            Map<String, Object> data = yaml.load(inputStream);
            for (Frame frame : clickGuiScreen.getFrames()) {
                if (data == null || data.get(StringUtils.capitalize(frame.getCategory().name().toLowerCase())) == null) continue;
                Map<String, Object> guiMap = (Map<String, Object>) data.get(StringUtils.capitalize(frame.getCategory().name().toLowerCase()));
                if (guiMap.containsKey("Open")) frame.setOpen((boolean) guiMap.get("Open"));
                if (guiMap.containsKey("X")) frame.setX((Integer) guiMap.get("X"));
                if (guiMap.containsKey("Y")) frame.setY((Integer) guiMap.get("Y"));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGui(ClickGuiScreen clickGuiScreen) {
        try {
            Map<String, Object> dataMap = new LinkedHashTreeMap<>();
            makeFile(null, "Gui");
            for (Frame frame : clickGuiScreen.getFrames()) {
                Map<String, Object> guiMap = new LinkedHashTreeMap<>();
                guiMap.put("Open", frame.isOpen());
                guiMap.put("X", frame.getX());
                guiMap.put("Y", frame.getY());
                dataMap.put(StringUtils.capitalize(frame.getCategory().name().toLowerCase()), guiMap);
            }
            PrintWriter writer = new PrintWriter(MAIN_FOLDER + "Gui.yml");
            yaml.dump(dataMap, writer);
            dataMap.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFriends() {
        try {
            if (!Files.exists(Paths.get(MAIN_FOLDER + "Friends.yml"))) return;
            InputStream inputStream = Files.newInputStream(Paths.get(MAIN_FOLDER + "Friends.yml"));
            for (UUID uuid : (List<UUID>) yaml.load(inputStream)) {
                Qubit.getFriendManger().addFriend(uuid);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFriends() {
        try {
            makeFile(null, "Friends");
            List<UUID> friendList = Qubit.getFriendManger().getFriendList();
            PrintWriter writer = new PrintWriter(MAIN_FOLDER + "Friends.yml");
            yaml.dump(friendList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
