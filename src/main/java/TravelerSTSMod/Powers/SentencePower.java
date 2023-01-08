package TravelerSTSMod.Powers;

import basemod.devcommands.relic.Relic;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.commons.lang3.ObjectUtils;
import org.lwjgl.Sys;

import java.util.ArrayList;

public class SentencePower extends AbstractPower {
    public interface IOnSentenceChanged {
        void onSentenceIncreased(AbstractPower sender, int sb, int sa);
    }

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
        this.priority = 5;

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
        flash();
        increaseSentence((AbstractPlayer)this.owner, 1);
    }

    @Override
    public void atStartOfTurn() {
        this.amount = 1;
    }

    @Override
    public void update(int slot) {
        super.update(slot);

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

        ArrayList<IOnSentenceChanged> l = new ArrayList<>();
        for (AbstractRelic r : p.relics) {
            if (r instanceof IOnSentenceChanged) {
                // 可能会改变powers，故先缓存
                l.add((IOnSentenceChanged) r);
            }
        }
        for (AbstractPower pw: p.powers) {
            if (pw instanceof IOnSentenceChanged) {
                // 可能会改变powers，故先缓存
                l.add((IOnSentenceChanged) pw);
            }
        }
        for (IOnSentenceChanged i : l) {
            i.onSentenceIncreased(ps, temp, ps.amount);
        }
        l.clear();

        for (AbstractCard c: p.discardPile.group) {
            if (c instanceof IOnSentenceChanged) {
                l.add((IOnSentenceChanged) c);
            }
        }
        for (IOnSentenceChanged i : l) {
            i.onSentenceIncreased(ps, temp, ps.amount);
        }
        l.clear();
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
