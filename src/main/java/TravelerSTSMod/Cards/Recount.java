package TravelerSTSMod.Cards;

import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.Powers.SentencePower;
import TravelerSTSMod.Powers.WhisperPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class Recount extends CustomCard {
    public static final String ID = "TravelerSTSMod:Recount";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TravelerSTSModResources/img/cards/Recount.png";
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Traveler.Enums.TRAVELER_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = -1;

    private boolean isCopy;
    private int x;

    public Recount() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.isSeen = true;
    }

//    public void calculateCardDamage(AbstractMonster mo) {
//        AbstractPlayer p = AbstractDungeon.player;
//        int x = EnergyPanel.totalCount + (this.upgraded ? 1 : 0);
//
//        if (p.hasRelic("Chemical X")) {
//            x += 2;
//        }
//        int d = (int)((Math.pow(x-1, 3) + 5*(x-1)) / 3 + Math.pow(x-1, 2) + 1);
//        // 本来已有的低语层数
//        for (AbstractPower pw : mo.powers) {
//            if (pw.ID.equals(WhisperPower.POWER_ID)){
//                d += (pw.amount - 1) * (x + 1) + 1;
//                break;
//            }
//        }
//
//        this.rawDescription = DESCRIPTION + CARD_STRINGS.EXTENDED_DESCRIPTION[0] + d +
//                CARD_STRINGS.EXTENDED_DESCRIPTION[1];
//        initializeDescription();
//    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.isCopy) {
            this.x = this.energyOnUse + (this.upgraded ? 1 : 0);
            if (p.hasRelic("Chemical X")) {
                this.x += 2;
                p.getRelic("Chemical X").flash();
            }

            addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        }

        if (this.x > 0) {
            Recount tmp = (Recount)makeStatEquivalentCopy();
            tmp.isCopy = true;
            tmp.x = this.x - 1;

            p.limbo.addToBottom(tmp);
            tmp.current_x = this.current_x;
            tmp.current_y = this.current_y;
            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0F;
            tmp.freeToPlayOnce = true;
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, tmp.x));

            addToBot(new ApplyPowerAction(m, p, new WhisperPower(m, p, this.x),
                    this.x, true, AbstractGameAction.AttackEffect.NONE));
        }

        if (!this.freeToPlayOnce)
            p.energy.use(EnergyPanel.totalCount);
    }

    public AbstractCard makeCopy() {
        return new Recount();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
