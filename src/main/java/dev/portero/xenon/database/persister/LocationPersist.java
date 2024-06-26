package dev.portero.xenon.database.persister;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.SQLException;

public class LocationPersist extends BaseDataType {

    private static final LocationPersist instance = new LocationPersist();

    private LocationPersist() {
        super(SqlType.LONG_STRING, new Class<?>[]{LocationPersist.class});
    }

    public static LocationPersist getSingleton() {
        return instance;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        if (javaObject == null) {
            return null;
        }

        Location loc = (Location) javaObject;
        String worldName = "world";

        if (loc.getWorld() != null) {
            worldName = loc.getWorld().getName();
        }

        return worldName + "/" + loc.getX() + "/" + loc.getY() + "/" + loc.getZ() + "/" + loc.getYaw() + "/" + loc.getPitch();
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return String.valueOf(defaultStr);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        String s = (String) sqlArg;

        if (s == null) {
            return null;
        }

        String[] params = s.split("/");

        return new Location(
            Bukkit.getWorld(params[0]),
            Double.parseDouble(params[1]),
            Double.parseDouble(params[2]),
            Double.parseDouble(params[3]),
            Float.parseFloat(params[4]),
            Float.parseFloat(params[5])
        );
    }
}
