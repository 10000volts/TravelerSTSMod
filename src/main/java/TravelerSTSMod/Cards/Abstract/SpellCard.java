package TravelerSTSMod.Cards.Abstract;

import TravelerSTSMod.Cards.QuickCasting;
import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.ModCore.TravelerMod;
import TravelerSTSMod.Powers.ChantCompletePower;
import TravelerSTSMod.Powers.ChantPower;
import basemod.abstracts.CustomCard;
import com.codedisaster.steamworks.SteamServerListRequest;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public abstract class SpellCard extends CustomCard implements IAtTurnStartWherever {
    protected int costInfluencedLastTurn;

    public SpellCard(String id, String name, String path, int c, String d,
                     CardType t, CardColor col, CardRarity r, CardTarget tgt, int influenced) {
        super(id, name, path, c, d, t, col, r, tgt);
        this.tags.add(Traveler.Enums.TRAVELER_SPELL);
        this.costInfluencedLastTurn = influenced;

        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null &&
                (AbstractDungeon.getCurrRoom()).monsters != null &&
                !(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead() &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                AbstractDungeon.player.hasPower(QuickCasting.ID) && c != -1) {
            this.cost = 0;
            this.costForTurn = 0;
        }
//        if (AbstractDungeon.player.hasPower(ChantPower.ID)) {
//            this.setCostForTurn(COST - 1);
//        }
    }

    // 修正cost
    @Override
    public void atTurnStartWherever() {
        this.cost += this.costInfluencedLastTurn;
        this.costInfluencedLastTurn = 0;
        resetAttributes();
        this.updateCost(this.cost);
        applyPowers();
    }

//    public void atTurnStart() {
//        super.atTurnStart();
//    }

    public void updateCost(int amt) {
        // X
        if (this.cost == -1) return;

        int tmpCost = amt;
        int diff = this.cost - this.costForTurn;
        if (AbstractDungeon.player.hasPower("TravelerSTSMod:QuickCasting")) {
            tmpCost = 0;
        }
        else if (tmpCost <= 0)
            tmpCost = 0;
        else {
            for (AbstractPower pw: AbstractDungeon.player.powers) {
                if (pw.ID.equals(QuickCasting.ID)) {
                    tmpCost = 0;
                    this.costInfluencedLastTurn = 0;
                }
                else if (pw.ID.equals(ChantPower.POWER_ID)) {
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
