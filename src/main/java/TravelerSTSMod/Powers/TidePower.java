package TravelerSTSMod.Powers;

import TravelerSTSMod.Actions.SelectCardAction;
import TravelerSTSMod.Actions.StoreAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class TidePower extends AbstractPower {
    public static final String POWER_ID = "TravelerSTSMod:Tide";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TidePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;

        // 添加一大一小两张能力图
        String path128 = "TravelerSTSModResources/img/powers/128/Tide.png";
        String path48 = "TravelerSTSModResources/img/powers/48/Tide.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.player.discardPile.isEmpty() && !AbstractDungeon.player.drawPile.isEmpty()) {
            ArrayList<AbstractCard> placeList = new ArrayList<>();
            this.addToBot(new SelectCardAction("作为容器", AbstractDungeon.player.discardPile, 1, cards -> {
                placeList.add(cards.get(0));
            }));
            this.addToBot(new SelectCardAction("寄存", AbstractDungeon.player.drawPile, amount, cards -> {
                this.addToTop(new StoreAction(cards, placeList.get(0)));
            }, true, true));
        }
    }
}
