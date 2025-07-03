# IPLogger Plugin

**This is a plugin for Minecraft servers (Spigot/Paper). It is not a mod and will not work on the client or with Forge/Fabric.**

A Minecraft server plugin for logging player IP addresses to a SQL database.

## Supported Minecraft Version

- Compatible with Minecraft 1.20.x (update this if your plugin supports a different version)

## Prerequisites

- Java 17 or higher
- [Maven](https://maven.apache.org/) (for building from source)
- A Minecraft server (Spigot/Paper recommended)
- A SQL database (MySQL, PostgreSQL, etc.)

## Build Instructions

1. Clone this repository:
   ```sh
   git clone https://github.com/Dylanfe/Minecraft-IPLogger.git
   ```

2. Build the plugin:
   ```sh
   mvn clean package
   ```

3. The compiled JAR will be located at `iplogger/target/iplogger-1.0-SNAPSHOT.jar`.

## Installation & Setup

### Step 1: Install the Plugin
1. Copy `iplogger-1.0-SNAPSHOT.jar` to your Minecraft server's `plugins/` folder.

### Step 2: Configure Database
1. Navigate to `plugins/IPLogger/` (created after first run).
2. Copy `config.example.yml` and rename it to `config.yml`.
3. Edit `config.yml` with your database details:
   ```yaml
   database:
     host: your-database-host.com
     name: your-database-name
     username: your-database-username
     password: your-secure-password
   ```

### Step 3: Start Server
1. Start or restart your Minecraft server.
2. Check the console for any errors during plugin loading.
3. The plugin will create necessary database tables automatically.

## Troubleshooting

- **Plugin not loading**: Check server console for error messages
- **Database connection failed**: Verify your database credentials and network connectivity
- **Permission errors**: Ensure the database user has appropriate permissions

## Configuration

Edit `config.yml` with your database connection details:
```yaml
database:
  host: your-database-host
  name: your-database-name
  username: your-database-username
  password: your-database-password
```

## Notes

- Do **not** commit your real `config.yml` to version control.
- Use `config.example.yml` as a template for sharing.

## Legal Disclaimer

This plugin collects and stores player IP addresses. Depending on your jurisdiction, collecting such data may be subject to privacy laws (such as GDPR, CCPA, etc.). You are responsible for informing users, obtaining any required consent, and complying with all applicable laws and regulations. The author provides this software as-is and assumes no liability for misuse.
