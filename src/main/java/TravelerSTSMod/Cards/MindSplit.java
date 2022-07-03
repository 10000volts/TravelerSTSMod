package TravelerSTSMod.Cards;

import TravelerSTSMod.Cards.Abstract.IOnTriggerAction;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Patches.ReturnToHandOncePatch;
import TravelerSTSMod.Powers.WhisperPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MindSplit extends CustomCard implements IOnTriggerAction {
    public static final String ID = "TravelerSTSMod:MindSplit";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/MindSplit.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 0;

    private boolean extraUpgrade;

    public MindSplit() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = 3;
        this.magicNumber = 3;
        extraUpgrade = false;

        this.isSeen = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                addToBot(new ApplyPowerAction(mo, p, new WhisperPower(mo, p, this.magicNumber), this.magicNumber,
                        true, AbstractGameAction.AttackEffect.NONE));
            }
        } else {
            addToBot(new ApplyPowerAction(m, p, new WhisperPower(m, p, this.magicNumber),
                    this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }

        // 复原
        AbstractCard c = this;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        c.returnToHand = false;
                        isDone = true;
                    }
                });
                isDone = true;
            }
        });

        if (extraUpgrade) {
            extraUpgrade = false;
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        AbstractCard c = new MindSplit();
        c.baseMagicNumber = this.baseMagicNumber;
        c.magicNumber = this.magicNumber;
        return c;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.target = CardTarget.ALL_ENEMY;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void onTriggerAction(int pos) {
        extraUpgrade = true;
        this.returnToHand = true;
        // ReturnToHandOncePatch.returnToHandOnceField.put(this, true);
    }
}
