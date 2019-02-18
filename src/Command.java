class Command
{
  enum Action
  {
    DEPOSIT, WITHDRAW
  }

  public Action action;
  public int amount;
  public boolean success;

  public Command(Action action, int amount)
  {
    this.action = action;
    this.amount = amount;
  }
}

class Account2
{
  public int balance;

  public void process(Command c)
  {
    switch(c.action) {
        case DEPOSIT:
            balance += c.amount;
            c.success = true;
            break;
        case WITHDRAW:
            if (c.amount > balance) {
                c.success = false;
            } else {                
                balance -= c.amount;
                c.success = true;
            }
            break;
        default:
            throw new IllegalStateException();
    }
  }
}
