package za.co.gundula.app.smartcitizen.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.time.LocalDateTime;

import static za.co.gundula.app.smartcitizen.entity.Property.TABLE_NAME;

/**
 * Created by kgundula on 2017/05/22.
 */

@Entity(tableName = TABLE_NAME)
public class Property {

    public static final String TABLE_NAME = "property";
    public static final String PROPERTY_CREATED_DATE = "property_created";
    public static final String PROPERTY_UPDATED_DATE = "property_updated";
    public static final String PROPERTY_OWNER = "property_owner";


    @PrimaryKey
    private int property_id;
    private String property_account_number;
    private String property_portion;
    @ColumnInfo (name = PROPERTY_OWNER)
    private String property_owner;
    private String property_bp;
    private String property_physical_address;
    @ColumnInfo(name = PROPERTY_CREATED_DATE)
    private String property_created;
    @ColumnInfo(name = PROPERTY_UPDATED_DATE)
    private String property_updated;

    @Ignore
    public Property() {
    }

    public Property(int property_id, String property_account_number, String property_portion, String property_owner, String property_bp, String property_physical_address, String property_created, String property_updated) {
        this.property_id = property_id;
        this.property_account_number = property_account_number;
        this.property_portion = property_portion;
        this.property_owner = property_owner;
        this.property_bp = property_bp;
        this.property_physical_address = property_physical_address;
        this.property_created = property_created;
        this.property_updated = property_updated;
    }

    public int getProperty_id() {
        return property_id;
    }

    public String getProperty_account_number() {
        return property_account_number;
    }

    public String getProperty_portion() {
        return property_portion;
    }

    public String getProperty_owner() {
        return property_owner;
    }

    public String getProperty_bp() {
        return property_bp;
    }

    public String getProperty_physical_address() {
        return property_physical_address;
    }

    public String getProperty_created() {
        return property_created;
    }

    public String getProperty_updated() {
        return property_updated;
    }

}
