package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.ISpecificSentence;
import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import TravelerSTSMod.Cards.Abstract.SpellCard;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Patches.SpellStormPatch;
import TravelerSTSMod.Powers.SentencePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ShuttleSpell extends SpellCard implements ISpecificSentence {
    public static final String ID = "TravelerSTSMod:ShuttleSpell";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/ShuttleSpell.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;

    public ShuttleSpell(int influenced) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, influenced);

        this.isSeen = true;

        this.baseMagicNumber = 3;
        this.magicNumber = 3;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber));
        if (SentencePower.getSentence(p) == 1) {
            this.exhaust = false;

            AbstractCard c = this;
            // 复原消耗属性
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    c.exhaust = true;
                    isDone = true;
                }
            });
        }
    }

    public AbstractCard makeCopy() {
        return new ShuttleSpell(this.costInfluencedLastTurn);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (SentencePower.getSentence(AbstractDungeon.player) == 1) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public int getSpecificSentence(boolean general) {
        return 1;
    }
}
