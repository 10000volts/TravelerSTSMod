package TravelerSTSMod.Patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static TravelerSTSMod.ModCore.TravelerMod.storageList;

@SpirePatch(clz = CardGroup.class, method = "addToHand", paramtypez = {AbstractCard.class})
public class TakeStoragePatch {

    @SpireInsertPatch(rloc = 0)
    public static void Insert(CardGroup self, AbstractCard c) {
        takeStorage(9 - AbstractDungeon.player.hand.size(), c);
    }

    private static void takeStorage(int space, AbstractCard c) {
        if (space == 0) return;
        if (!storageList.isEmpty()) {
            ArrayList<AbstractCard> placeList = new ArrayList<>();
            for (ArrayList tmp : storageList) {
                placeList.add((AbstractCard) tmp.get(0));
            }
            if (placeList.contains(c)) {
                ArrayList<AbstractCard> storages = storageList.get(placeList.indexOf(c));
                ArrayList<AbstractCard> taken = new ArrayList<>();
                for (AbstractCard storage : storages) {
                    if (storage != c) {
                        if (space-- > 0) {
                            takeStorage(space, storage);
                            AbstractDungeon.player.hand.group.add(storage);
                            taken.add(storage);
                        } else break;
                    }
                }
                for (AbstractCard storage : taken) {
                    storages.remove(storage);
                }
                if (storages.size() == 1) {
                    storageList.remove(storages);
                }
            }
        }
    }
}