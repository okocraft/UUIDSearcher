package net.okocraft.uuidsearcher;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class UUIDSearcher extends JavaPlugin {

    private static final String PERMISSION = "uuidsearcher.command";
    private static final Component NO_PERMISSION = Component.text("No permission.", NamedTextColor.RED);
    private static final Component NO_ARGUMENT = Component.text("/searchforuuid <name>", NamedTextColor.RED);
    private static final Component UUID_NOT_FOUND = Component.text("UUID not found.", NamedTextColor.RED);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        getServer().getScheduler().runTaskAsynchronously(this, () -> runCommand(sender, args));
        return true;
    }

    private void runCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage(NO_PERMISSION);
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(NO_ARGUMENT);
            return;
        }

        var uuid = Bukkit.getPlayerUniqueId(args[0]);

        if (uuid != null) {
            var uuidString = uuid.toString();
            var click = ClickEvent.copyToClipboard(uuidString);
            var hover = HoverEvent.showText(Component.text("Click to copy"));

            sender.sendMessage(
                    Component.text(args[0], NamedTextColor.AQUA)
                            .append(Component.text("'s uuid: ", NamedTextColor.GRAY))
                            .append(Component.text('[' + uuidString + ']', NamedTextColor.AQUA).clickEvent(click).hoverEvent(hover))
            );
        } else {
            sender.sendMessage(UUID_NOT_FOUND);
        }
    }
}
