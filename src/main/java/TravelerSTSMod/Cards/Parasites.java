package TravelerSTSMod.Cards;

import TravelerSTSMod.Actions.ParasitesAction;
import TravelerSTSMod.Cards.Abstract.IStoredCard;
import TravelerSTSMod.Characters.Traveler;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Parasites extends CustomCard implements IStoredCard {

    public static final String ID = "TravelerSTSMod:Parasites";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Parasites.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final int COST = -1;
    private static final int DAMAGE = 6;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADE_MAGIC_NUMBER = 3;

    public Parasites() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC_NUMBER;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ParasitesAction(p, this, this.multiDamage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
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
        this.baseDamage += this.magicNumber;
        if (this.baseDamage < 0) {
            this.baseDamage = 0;
        }
        initializeDescription();
    }
}