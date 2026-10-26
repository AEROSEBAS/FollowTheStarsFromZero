package aero.ftsfz.client.screen.tab;

import java.util.List;
import java.util.function.Supplier;

public final class MenuTabs {
    private MenuTabs() {}

    public static List<Supplier<MenuTab>> all() {
        return List.of(
                StatsTab::new,
                SkillsTab::new
                // InventoryTab::new, QuestsTab::new ... en el futuro
        );
    }
}