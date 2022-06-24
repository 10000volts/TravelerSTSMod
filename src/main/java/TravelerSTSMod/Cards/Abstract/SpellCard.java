package TravelerSTSMod.Cards.Abstract;

import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.ModCore.TravelerMod;
import TravelerSTSMod.Powers.ChantCompletePower;
import TravelerSTSMod.Powers.ChantPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class SpellCard extends CustomCard {
    protected int costInfluencedLastTurn;

    public SpellCard(String id, String name, String path, int c, String d,
                     CardType t, CardColor col, CardRarity r, CardTarget tgt, int influenced) {
        super(id, name, path, c, d, t, col, r, tgt);
        this.tags.add(Traveler.Enums.TRAVELER_SPELL);
        this.costInfluencedLastTurn = influenced;
//        if (AbstractDungeon.player.hasPower(ChantPower.ID)) {
//            this.setCostForTurn(COST - 1);
//        }
    }

    // 修正cost
    public void atTurnStart() {
        super.atTurnStart();
        this.cost += this.costInfluencedLastTurn;
        this.costInfluencedLastTurn = 0;
        resetAttributes();
        this.updateCost(this.cost);
        applyPowers();
    }

    public void updateCost(int amt) {
        // X
        if (this.cost == -1) return;

        int tmpCost = 0;
        int diff = this.cost - this.costForTurn;
        tmpCost += amt;
        if (AbstractDungeon.player.hasPower("TravelerSTSMod:QuickCasting")) {
            tmpCost = 0;
        }
        else if (tmpCost <= 0)
            tmpCost = 0;
        else {
            for (AbstractPower pw: AbstractDungeon.player.powers) {
                if (pw.ID.equals(ChantPower.POWER_ID)) {
                    int m = Math.min(tmpCost, pw.amount);
                    tmpCost -= m;
                    this.costInfluencedLastTurn += m;
                    break;
                }
            }
        }
        if (tmpCost != this.cost) {
            this.isCostModified = true;
            this.cost = tmpCost;
            this.costForTurn = this.cost - diff;
            if (this.costForTurn < 0)
                this.costForTurn = 0;
        }
    }
}
