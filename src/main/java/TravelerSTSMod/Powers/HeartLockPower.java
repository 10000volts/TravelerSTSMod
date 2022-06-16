package TravelerSTSMod.Powers;

import TravelerSTSMod.Powers.Abstract.SentenceXPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HeartLockPower extends SentenceXPower {
    public static final String POWER_ID = "TravelerSTSMod:HeartLock";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HeartLockPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.priority = 100;

        // 添加一大一小两张能力图
        String path128 = "TravelerSTSModResources/img/powers/128/HeartLock.png";
        String path48 = "TravelerSTSModResources/img/powers/48/HeartLock.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onSentenceIncreased(AbstractPower sender, int sb, int s) {
        if (s >= 7 && sb < 7) {
            flash();
            sender.amount = 1;
            addToBot(new GainBlockAction(this.owner, this.amount, Settings.FAST_MODE));
        }
    }
}
