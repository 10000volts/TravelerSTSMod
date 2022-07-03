package TravelerSTSMod.Patches;

import TravelerSTSMod.Cards.Inking;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

import java.awt.*;


@SpirePatch(clz = CombatRewardScreen.class, method = "setupItemReward", paramtypez =
        {})
public class InkingPatch {
    @SpireInsertPatch(rloc = 17)
    public static void Insert(CombatRewardScreen self) {
        if ((AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoom ||
                (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite)) &&
                        !(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss)) {
            System.out.println("INKINGPAAAAAAAAAAATCH");
            System.out.println(Inking.played);
            if (AbstractDungeon.player.hasPower("TravelerSTSMod:Inking") ||
                    Inking.played) {
                RewardItem cardReward = new RewardItem();
                if (cardReward.cards.size() > 0)
                    self.rewards.add(cardReward);
            }
        }
    }
}
