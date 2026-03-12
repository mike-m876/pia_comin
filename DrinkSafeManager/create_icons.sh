#!/bin/bash
# Script para crear todos los íconos vectoriales de DrinkSafe Manager

DRAWABLE_DIR="/home/claude/DrinkSafeManager/app/src/main/res/drawable"

# ic_add_drink.xml
cat > "$DRAWABLE_DIR/ic_add_drink.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M20,3H4v10c0,2.21 1.79,4 4,4h6c2.21,0 4,-1.79 4,-4v-3h2c1.11,0 2,-0.89 2,-2V5c0,-1.11 -0.89,-2 -2,-2zM20,8h-2V5h2v3zM4,19h16v2H4z"/>
</vector>
EOF

# ic_database.xml
cat > "$DRAWABLE_DIR/ic_database.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M12,3C7.58,3 4,4.79 4,7C4,9.21 7.58,11 12,11C16.42,11 20,9.21 20,7C20,4.79 16.42,3 12,3ZM4,9V12C4,14.21 7.58,16 12,16C16.42,16 20,14.21 20,12V9C20,11.21 16.42,13 12,13C7.58,13 4,11.21 4,9ZM4,14V17C4,19.21 7.58,21 12,21C16.42,21 20,19.21 20,17V14C20,16.21 16.42,18 12,18C7.58,18 4,16.21 4,14Z"/>
</vector>
EOF

# ic_settings.xml
cat > "$DRAWABLE_DIR/ic_settings.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M19.14,12.94c0.04,-0.3 0.06,-0.61 0.06,-0.94c0,-0.32 -0.02,-0.64 -0.07,-0.94l2.03,-1.58c0.18,-0.14 0.23,-0.41 0.12,-0.61l-1.92,-3.32c-0.12,-0.22 -0.37,-0.29 -0.59,-0.22l-2.39,0.96c-0.5,-0.38 -1.03,-0.7 -1.62,-0.94L14.4,2.81c-0.04,-0.24 -0.24,-0.41 -0.48,-0.41h-3.84c-0.24,0 -0.43,0.17 -0.47,0.41L9.25,5.35C8.66,5.59 8.12,5.92 7.63,6.29L5.24,5.33c-0.22,-0.08 -0.47,0 -0.59,0.22L2.74,8.87C2.62,9.08 2.66,9.34 2.86,9.48l2.03,1.58C4.84,11.36 4.8,11.69 4.8,12s0.02,0.64 0.07,0.94l-2.03,1.58c-0.18,0.14 -0.23,0.41 -0.12,0.61l1.92,3.32c0.12,0.22 0.37,0.29 0.59,0.22l2.39,-0.96c0.5,0.38 1.03,0.7 1.62,0.94l0.36,2.54c0.05,0.24 0.24,0.41 0.48,0.41h3.84c0.24,0 0.44,-0.17 0.47,-0.41l0.36,-2.54c0.59,-0.24 1.13,-0.56 1.62,-0.94l2.39,0.96c0.22,0.08 0.47,0 0.59,-0.22l1.92,-3.32c0.12,-0.22 0.07,-0.47 -0.12,-0.61L19.14,12.94zM12,15.6c-1.98,0 -3.6,-1.62 -3.6,-3.6s1.62,-3.6 3.6,-3.6s3.6,1.62 3.6,3.6S13.98,15.6 12,15.6z"/>
</vector>
EOF

# ic_arrow_right.xml
cat > "$DRAWABLE_DIR/ic_arrow_right.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M10,6L8.59,7.41 13.17,12l-4.58,4.59L10,18l6,-6z"/>
</vector>
EOF

# ic_arrow_back.xml
cat > "$DRAWABLE_DIR/ic_arrow_back.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M20,11H7.83l5.59,-5.59L12,4l-8,8 8,8 1.41,-1.41L7.83,13H20v-2z"/>
</vector>
EOF

# ic_refresh.xml
cat > "$DRAWABLE_DIR/ic_refresh.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M17.65,6.35C16.2,4.9 14.21,4 12,4c-4.42,0 -7.99,3.58 -7.99,8s3.57,8 7.99,8c3.73,0 6.84,-2.55 7.73,-6h-2.08c-0.82,2.33 -3.04,4 -5.65,4 -3.31,0 -6,-2.69 -6,-6s2.69,-6 6,-6c1.66,0 3.14,0.69 4.22,1.78L13,11h7V4l-2.35,2.35z"/>
</vector>
EOF

# ic_connection_on.xml
cat > "$DRAWABLE_DIR/ic_connection_on.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="40dp" android:height="40dp"
    android:viewportWidth="40" android:viewportHeight="40">
    <path android:fillColor="#4CAF50"
        android:pathData="M20,20m-16,0a16,16 0,1 1,32 0a16,16 0,1 1,-32 0"/>
    <path android:fillColor="#FFFFFF"
        android:pathData="M16,20.5l3,3 7,-7-1.5-1.5-5.5,5.5-1.5-1.5z"/>
</vector>
EOF

# ic_connection_off.xml
cat > "$DRAWABLE_DIR/ic_connection_off.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="40dp" android:height="40dp"
    android:viewportWidth="40" android:viewportHeight="40">
    <path android:fillColor="#F44336"
        android:pathData="M20,20m-16,0a16,16 0,1 1,32 0a16,16 0,1 1,-32 0"/>
    <path android:fillColor="#FFFFFF"
        android:pathData="M26,15.41L24.59,14 20,18.59 15.41,14 14,15.41 18.59,20 14,24.59 15.41,26 20,21.41 24.59,26 26,24.59 21.41,20z"/>
</vector>
EOF

# ic_connection_checking.xml
cat > "$DRAWABLE_DIR/ic_connection_checking.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="40dp" android:height="40dp"
    android:viewportWidth="40" android:viewportHeight="40">
    <path android:fillColor="#2196F3"
        android:pathData="M20,20m-16,0a16,16 0,1 1,32 0a16,16 0,1 1,-32 0"/>
    <path android:fillColor="#FFFFFF"
        android:pathData="M21,13h-2v8l7,4.16 1,-1.63 -6,-3.53z"/>
</vector>
EOF

# ic_search.xml
cat > "$DRAWABLE_DIR/ic_search.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M15.5,14h-0.79l-0.28,-0.27C15.41,12.59 16,11.11 16,9.5 16,5.91 13.09,3 9.5,3S3,5.91 3,9.5 5.91,16 9.5,16c1.61,0 3.09,-0.59 4.23,-1.57l0.27,0.28v0.79l5,4.99L20.49,19l-4.99,-5zM9.5,14C7.01,14 5,11.99 5,9.5S7.01,5 9.5,5 14,7.01 14,9.5 11.99,14 9.5,14z"/>
</vector>
EOF

# ic_delete.xml
cat > "$DRAWABLE_DIR/ic_delete.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M6,19c0,1.1 0.9,2 2,2h8c1.1,0 2,-0.9 2,-2V7H6v12zM19,4h-3.5l-1,-1h-5l-1,1H5v2h14V4z"/>
</vector>
EOF

# ic_label.xml
cat > "$DRAWABLE_DIR/ic_label.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M17.63,5.84C17.27,5.33 16.67,5 16,5L5,5.01C3.9,5.01 3,5.9 3,7v10c0,1.1 0.9,1.99 2,1.99L16,19c0.67,0 1.27,-0.33 1.63,-0.84L22,12l-4.37,-6.16z"/>
</vector>
EOF

# ic_brand.xml
cat > "$DRAWABLE_DIR/ic_brand.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M12,1L3,5v6c0,5.55 3.84,10.74 9,12 5.16,-1.26 9,-6.45 9,-12V5l-9,-4zm0,4l5,2.18V11c0,3.5 -2.33,6.79 -5,7.93 -2.67,-1.14 -5,-4.43 -5,-7.93V7.18L12,5z"/>
</vector>
EOF

# ic_notes.xml
cat > "$DRAWABLE_DIR/ic_notes.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M3,10h11v2H3zM3,6h11v2H3zM3,14h7v2H3zM14,14v6l2.5,-2.5L19,20v-6h-5z"/>
</vector>
EOF

# ic_sensor.xml
cat > "$DRAWABLE_DIR/ic_sensor.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M12,18.5A6.5,6.5 0,0 1,5.5 12,6.5 6.5,0 0,1 12,5.5,6.5 6.5,0 0,1 18.5,12,6.5 6.5,0 0,1 12,18.5M12,2A10,10 0,0 0,2 12A10,10 0,0 0,12 22,10 10,0 0,0 22,12,10 10,0 0,0 12,2M12,7A5,5 0,0 0,7 12,5 5,0 0,0 12,17,5 5,0 0,0 17,12,5 5,0 0,0 12,7Z"/>
</vector>
EOF

# ic_info.xml
cat > "$DRAWABLE_DIR/ic_info.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10 10,-4.48 10,-10S17.52,2 12,2zm1,15h-2v-6h2v6zm0,-8h-2V7h2v2z"/>
</vector>
EOF

# ic_vodka.xml
cat > "$DRAWABLE_DIR/ic_vodka.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M7,2v2h1v14c0,1.1 0.9,2 2,2h4c1.1,0 2,-0.9 2,-2V4h1V2H7zM15,4v2h-6V4h6zm-1,14h-4v-8h4v8z"/>
</vector>
EOF

# ic_tequila.xml
cat > "$DRAWABLE_DIR/ic_tequila.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M9,2L9,3H5v2l1,16h12L19,5h-4V2H9zM17.18,7l-0.75,12H7.57L6.82,7h10.36z"/>
</vector>
EOF

# ic_drink_generic.xml
cat > "$DRAWABLE_DIR/ic_drink_generic.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M20,3H4v10c0,2.21 1.79,4 4,4h6c2.21,0 4,-1.79 4,-4v-3h2c1.11,0 2,-0.89 2,-2V5c0,-1.11 -0.89,-2 -2,-2zM20,8h-2V5h2v3zM4,19h16v2H4z"/>
</vector>
EOF

# ic_alcohol.xml
cat > "$DRAWABLE_DIR/ic_alcohol.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M8.1,13.34l2.83,-2.83L3.91,3.5 2.5,4.91l5.6,8.43zM13.41,13c1.57,0 3.06,-0.61 4.17,-1.72l1.42,-1.42c0.39,-0.39 0.39,-1.02 0,-1.41L14.6,4.08c-0.39,-0.39 -1.02,-0.39 -1.41,0l-1.42,1.41 2.5,2.5 -2.12,2.12 -2.5,-2.5 -1.79,1.79c-1.13,1.13 -1.73,2.64 -1.73,4.2h-2.5v3h18v-3h-8.22z"/>
</vector>
EOF

# ic_conductivity.xml
cat > "$DRAWABLE_DIR/ic_conductivity.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M7,2v11h3v9l7,-12h-4l4,-8z"/>
</vector>
EOF

# ic_temperature.xml
cat > "$DRAWABLE_DIR/ic_temperature.xml" << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp"
    android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="#FF000000"
        android:pathData="M15,13V5c0,-1.66 -1.34,-3 -3,-3S9,3.34 9,5v8c-1.21,0.91 -2,2.37 -2,4 0,2.76 2.24,5 5,5s5,-2.24 5,-5c0,-1.63 -0.79,-3.09 -2,-4zM11,5c0,-0.55 0.45,-1 1,-1s1,0.45 1,1h-1v1h1v2h-1v1h1v2h-2V5z"/>
</vector>
EOF

echo "✓ Todos los íconos creados correctamente"
