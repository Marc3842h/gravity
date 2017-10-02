package club.wontfix.gravity.commands.impl;

import club.wontfix.gravity.Gravity;
import club.wontfix.gravity.commands.Command;

public class RemoveVerifyIDCommand extends Command {

    public RemoveVerifyIDCommand() {
        super(new String[]{"removeverifyid", "removeid", "removeverify", "removeuser", "remove"},
                "Removes a registered / pending Verify ID from the database",
                "remove <verify id>");
    }

    @Override
    public boolean execute(String label, String[] args) {
        if (args.length == 1) {
            String verifyID = args[0];

            if (Gravity.getInstance().getEasyDatabase().isVerifyIDBound(verifyID)) {
                Gravity.getInstance().getEasyDatabase().unregisterUserUsingVerifyID(verifyID);

                Gravity.getInstance().getLogger().info("Successfully removed {0} from the database.", verifyID);
                return true;
            }

            if (Gravity.getInstance().getEasyDatabase().isVerifyIDWaiting(verifyID)) {
                Gravity.getInstance().getEasyDatabase().unregisterWaitingVerifyID(verifyID);

                Gravity.getInstance().getLogger().info("Successfully revoked {0} from the pending database.", verifyID);
                return true;
            }

            Gravity.getInstance().getLogger().error("Inputted Verify ID has not been found in the database.");
            return true;
        }

        return false;
    }

}