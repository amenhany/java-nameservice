package org.nameservice.server;

import java.util.concurrent.ConcurrentHashMap;

public class ServiceRegistry {

    private final ConcurrentHashMap<String, String> nameToIp = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> ipToName = new ConcurrentHashMap<>();

    public synchronized String register(String name, String ip) {
        if (ipToName.containsKey(ip))
            return "ERROR: IP Already Registered";

        nameToIp.put(name, ip);
        ipToName.put(ip, name);
        return "SUCCESS";
    }

    public String resolve(String name) {
        String ip = nameToIp.get(name);
        return (ip == null) ? "ERROR: Not Found" : ip;
    }

    public synchronized String deregister(String name) {
        String ip = nameToIp.remove(name);
        if (ip != null) ipToName.remove(ip);
        return "SUCCESS";
    }
}