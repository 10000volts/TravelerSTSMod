package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.PersonalityCard;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.PrideFormPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Sloth extends PersonalityCard {
    public static final String ID = "TravelerSTSMod:Sloth";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Sloth.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;

    public Sloth(boolean ethereal) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ethereal);

        this.isSeen = true;

        this.baseMagicNumber = 3;
        this.magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 2));
    }

    public AbstractCard makeCopy() {
        return new Sloth(this.isEthereal);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (isEthereal) {
            this.rawDescription = PrideFormPower.DESCRIPTIONS[1] + DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void onAct(AbstractCard c, ArrayList<AbstractCard> cards, AbstractMonster m) {
        super.onAct(c, cards, m);
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
    }
}
