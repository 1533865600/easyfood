package com.medical.ehr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginManager {
    private Map<String, MedicalPlugin> pluginMap = new HashMap<>();

    public void registerPlugin(MedicalPlugin plugin) {
        pluginMap.put(plugin.getPluginName(), plugin);
        System.out.println("插件注册成功：" + plugin.getPluginName());
    }

    public List<String> listPlugins() {
        return new ArrayList<>(pluginMap.keySet());
    }

    public void executePlugin(String name, EHRRecord record) {
        MedicalPlugin plugin = pluginMap.get(name);
        if (plugin != null) {
            plugin.execute(record);
        }
    }
}