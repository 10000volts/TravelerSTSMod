package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.IStoredCard;
import TravelerSTSMod.Characters.Traveler;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MemoryPieces extends CustomCard implements IStoredCard {

    public static final String ID = "TravelerSTSMod:MemoryPieces";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/MemoryPieces.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 0;

    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADE_MAGIC_NUMBER = 1;

    public MemoryPieces() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC_NUMBER;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }

    @Override
    public void triggerWhenStored() {
        this.addToBot(new GainEnergyAction(this.magicNumber));
    }
}