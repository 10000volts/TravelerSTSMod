package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.SentencePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Envy extends PersonalityCard {
    public static final String ID = "TravelerSTSMod:Envy";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Envy.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;

    private boolean waitForAct;

    public Envy(boolean ethereal) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ethereal);

        this.isSeen = true;

        this.baseMagicNumber = 2;
        this.magicNumber = 2;
        waitForAct = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Envy(this.isEthereal);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void onAct(AbstractCard c, ArrayList<AbstractCard> cards, AbstractMonster m) {
        super.onAct(c, cards, m);
        this.waitForAct = true;
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (this.waitForAct) {
            this.waitForAct = false;
            SentencePower.increaseSentence(AbstractDungeon.player, 1);
        }
    }
}
