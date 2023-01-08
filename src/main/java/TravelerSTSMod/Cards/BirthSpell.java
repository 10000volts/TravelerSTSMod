package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.SpellCard;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.SentencePower;
import TravelerSTSMod.Relics.BookAndQuill;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BirthSpell extends SpellCard {
    public static final String ID = "TravelerSTSMod:BirthSpell";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/BirthSpell.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    private int magicNumber2;

    public BirthSpell(int influenced) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, influenced);

        this.isSeen = true;

        this.baseMagicNumber = 2;
        this.magicNumber = 2;
        this.magicNumber2 = 3;
        this.isEthereal = true;
        this.exhaust = true;
        FleetingField.fleeting.set(this, true);

        this.tags.add(AbstractCard.CardTags.HEALING);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.magicNumber2 += 3;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BirthSpell(this.costInfluencedLastTurn);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        BookAndQuill.increaseInk(p, this.magicNumber);
        addToBot(new HealAction(p, p, this.magicNumber2));
    }
}
