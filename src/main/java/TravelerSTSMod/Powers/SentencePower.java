package TravelerSTSMod.Powers;

import TravelerSTSMod.Powers.Abstract.SentenceXPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.commons.lang3.ObjectUtils;
import org.lwjgl.Sys;

public class SentencePower extends AbstractPower {
    public static final String POWER_ID = "TravelerSTSMod:Sentence";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SentencePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 1;

        // 添加一大一小两张能力图
        String path128 = "TravelerSTSModResources/img/powers/128/Sentence.png";
        String path48 = "TravelerSTSModResources/img/powers/48/Sentence.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void stackPower(int stackAmount) {

    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        increaseSentence((AbstractPlayer)this.owner, 1);
    }

    @Override
    public void atStartOfTurn() {
        this.amount = 1;
    }

    public static void increaseSentence(AbstractPlayer p, int n) {
        AbstractPower ps = null;
        int temp = 0;

        if (n == 0) return;
        for (AbstractPower pw: p.powers) {
            if (pw.ID.equals("TravelerSTSMod:Sentence")) {
                temp = pw.amount;
                pw.amount += n;
                ps = pw;
                break;
            }
        }

        if (n < 0) return;
        System.out.println("sxp: ");
        for (AbstractPower pw: p.powers) {
            if (pw instanceof SentenceXPower) {
                ((SentenceXPower) pw).onSentenceIncreased(ps, temp, ps.amount);
            }
        }
    }

    public static int getSentence(AbstractPlayer p) {
        for (AbstractPower pw: p.powers) {
            if (pw.ID.equals("TravelerSTSMod:Sentence")) {
                return pw.amount;
            }
        }
        return 0;
    }
}
