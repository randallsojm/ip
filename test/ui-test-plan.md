# Level 4 UI test plan

## Test case: Add, list, mark, and unmark task types

### Aim
Verify Level 4 commands create the correct subclasses, preserve details in `list`, and show the full task format when marking and unmarking.

### Input
```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
mark 2
unmark 2
bye
```

### Expected output
```text
 _   _  _____  __  __  _   _  _____
| \ | || ____| \ \/ / | | | ||  ___|
|  \| ||  _|    \  /  | | | || |___ 
| |\  || |___   /  \  | |_| | ___| |
|_| \_||_____| /_/\_\  \___/ |____/ 
Hello! I'm Nexus.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [D][ ] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
