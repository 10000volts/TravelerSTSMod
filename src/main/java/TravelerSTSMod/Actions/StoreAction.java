package TravelerSTSMod.Actions;

import TravelerSTSMod.Cards.Abstract.IStoredCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

import static TravelerSTSMod.ModCore.TravelerMod.storageList;

public class StoreAction extends AbstractGameAction {
    AbstractCard storage = null;
    AbstractCard place = null;

    public StoreAction(AbstractCard storage, AbstractCard place) {
        this.storage = storage;
        this.place = place;
    }

    public StoreAction(ArrayList<AbstractCard> storage, AbstractCard place) {
        if (!storage.isEmpty() && place != null) {
            Collections.reverse(storage);
            for (AbstractCard c : storage) {
                this.addToTop(new StoreAction(c, place));
            }
        }
    }

    @Override
    public void update() {
        if (storage != null && place != null) {
            ArrayList<AbstractCard> placeList = new ArrayList<>();
            if (!storageList.isEmpty()) {
                for (ArrayList tmp : storageList) {
                    placeList.add((AbstractCard) tmp.get(0));
                }
            }
            if (placeList.contains(place)) {
                storageList.get(placeList.indexOf(place)).add(storage);
            } else {
                ArrayList<AbstractCard> newplace = new ArrayList<>();
                newplace.add(place);
                newplace.add(storage);
                storageList.add(newplace);
            }
            AbstractDungeon.player.hand.group.remove(storage);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.drawPile.group.remove(storage);
            AbstractDungeon.player.discardPile.group.remove(storage);
            AbstractDungeon.player.exhaustPile.group.remove(storage);
            if (storage instanceof IStoredCard) {
                ((IStoredCard) storage).triggerWhenStored();
            }
        }
        this.isDone = true;
    }
}
