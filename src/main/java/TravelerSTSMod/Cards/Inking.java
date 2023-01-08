package TravelerSTSMod.Cards;

import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.InkingPower;
import TravelerSTSMod.Powers.VoiceOfDarkPower;
import TravelerSTSMod.Relics.BookAndQuill;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Inking extends CustomCard implements CustomSavable<Boolean> {
    public static final String ID = "TravelerSTSMod:Inking";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Inking.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;

    public static boolean played = false;

    public Inking()  {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.isSeen = true;

        this.isEthereal = true;
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (BookAndQuill.costInk(p, 1)) {
            played = true;
            addToBot(new ApplyPowerAction(p, p, new InkingPower(p)));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;
        return BookAndQuill.enoughInk(1);
    }

    public AbstractCard makeCopy() {
        return new Inking();
    }

    @Override
    public Boolean onSave() {
        System.out.println("ONSAAAAAAAAAAAAAAAAAAVE");
        System.out.println(played);
        return played;
    }

    @Override
    public void onLoad(Boolean aBoolean) {
        played = aBoolean;
        System.out.println("ONLOOOOOOOOOOOOOOAD");
        System.out.println(played);
    }
}
